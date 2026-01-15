package com.personal.marketnote.community.service.post;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.AuthorityValidator;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.post.*;
import com.personal.marketnote.community.exception.PostNotFoundException;
import com.personal.marketnote.community.port.in.command.post.GetPostsCommand;
import com.personal.marketnote.community.port.in.result.post.GetPostsResult;
import com.personal.marketnote.community.port.in.result.post.PostItemResult;
import com.personal.marketnote.community.port.in.result.post.PostProductInfoResult;
import com.personal.marketnote.community.port.in.usecase.post.GetPostUseCase;
import com.personal.marketnote.community.port.out.post.FindPostPort;
import com.personal.marketnote.community.port.out.product.FindProductByPricePolicyPort;
import com.personal.marketnote.community.port.out.result.product.ProductInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetPostService implements GetPostUseCase {
    private final FindPostPort findPostPort;
    private final FindProductByPricePolicyPort findProductByPricePolicyPort;

    @Override
    public boolean existsPost(Long id) {
        return findPostPort.existsById(id);
    }

    @Override
    public GetPostsResult getPosts(GetPostsCommand command) {
        Sort.Direction sortDirection = FormatValidator.hasValue(command.sortDirection())
                ? command.sortDirection()
                : Sort.Direction.DESC;
        PostSortProperty sortProperty = resolveSortProperty(command);
        Pageable pageable = PageRequest.of(0, command.pageSize() + 1, Sort.by(sortDirection, getSortField(sortProperty)));

        Posts posts = getBoardPosts(command, pageable, sortProperty);

        Long totalElements = null;
        if (!FormatValidator.hasValue(command.cursor())) {
            totalElements = findPostPort.count(
                    command.userId(),
                    command.board(),
                    command.searchTarget(),
                    command.searchKeyword()
            );
        }

        return generatePage(command, posts, totalElements);
    }

    private Posts getBoardPosts(GetPostsCommand command, Pageable pageable, PostSortProperty sortProperty) {
        Board board = command.board();
        PostTargetType targetType = command.targetType();
        boolean isDesc = Sort.Direction.DESC.equals(command.sortDirection());
        Long userId = command.userId();

        // 비회원 전용 게시판이거나 상품 상세 정보의 문의 게시판인 경우
        if (board.isNonMemberViewBoard() || FormatValidator.hasValue(targetType)) {
            return findPostPort.findPosts(
                    board,
                    command.category(),
                    targetType,
                    command.targetId(),
                    command.cursor(),
                    pageable,
                    isDesc,
                    sortProperty,
                    userId,
                    command.filter(),
                    command.filterValue(),
                    command.searchTarget(),
                    command.searchKeyword()
            );
        }

        // 나의 상품 문의 게시판이거나 나의 1:1 문의 게시판인 경우
        return findPostPort.findUserPosts(
                userId,
                board,
                command.cursor(),
                pageable,
                isDesc,
                sortProperty,
                command.searchTarget(),
                command.searchKeyword()
        );
    }

    private GetPostsResult generatePage(GetPostsCommand command, Posts posts, Long totalElementsOverride) {
        Long totalElements = totalElementsOverride;
        PostTargetType targetType = command.targetType();
        Board board = command.board();

        if (!FormatValidator.hasValue(totalElements) && !FormatValidator.hasValue(command.cursor())) {
            totalElements = findPostPort.count(
                    board,
                    command.category(),
                    targetType,
                    command.targetId(),
                    command.userId(),
                    command.filter(),
                    command.filterValue(),
                    command.searchTarget(),
                    command.searchKeyword()
            );
        }

        // 상품 문의 게시판인 경우 각 게시글의 주문 상품 정보 조회
        Map<Long, ProductInfoResult> reviewedProducts = board.isProductInquery()
                ? getProductInfo(posts.getPosts())
                : Map.of();

        List<Post> filteredPosts = posts.getPosts();

        // 나의 상품 문의 게시판 또는 1:1 문의 게시판인 경우 검색어 적용
        if (board.isOneOnOneInquery() || board.isProductInquery() && !FormatValidator.hasValue(targetType)) {
            if (FormatValidator.hasValue(command.searchKeyword())) {
                PostSearchTarget keywordCategory = command.searchTarget();
                String searchKeyword = command.searchKeyword().toLowerCase();
                filteredPosts = filteredPosts.stream()
                        .filter(post -> matchesKeyword(post, keywordCategory, searchKeyword))
                        .toList();
            }
        }

        boolean hasNext = filteredPosts.size() > command.pageSize();
        List<Post> pagedPosts = hasNext
                ? filteredPosts.subList(0, command.pageSize())
                : filteredPosts;

        Long nextCursor = null;
        if (FormatValidator.hasValue(pagedPosts)) {
            nextCursor = pagedPosts.getLast().getId();
        }

        List<PostItemResult> postItems = pagedPosts.stream()
                .map(post -> {
                    if (!FormatValidator.hasValue(post.getTargetId())) {
                        return PostItemResult.from(post);
                    }

                    // 상품 문의 게시판인 경우 각 게시글의 상품 정보 포함
                    ProductInfoResult productInfoResult = reviewedProducts.get(post.getTargetId());
                    PostItemResult postItemResult = PostItemResult.from(
                            post,
                            PostProductInfoResult.from(productInfoResult)
                    );

                    // 상품 상세 정보 페이지의 상품 문의 게시판인 경우 비밀글 필터링
                    if (FormatValidator.hasValue(targetType)) {
                        Long sellerId = FormatValidator.hasValue(productInfoResult)
                                ? productInfoResult.sellerId()
                                : null;
                        if (!adminOrSeller(command.principal(), command.userId(), sellerId)) {
                            postItemResult.maskPrivatePost(command.userId());
                        }
                    }

                    // 게시글의 답글 목록 추가
                    if (!postItemResult.isMasked() && post.hasReplies()) {
                        postItemResult.addReplies(post);
                    }

                    return postItemResult;
                })
                .toList();

        return GetPostsResult.of(hasNext, nextCursor, totalElements, postItems);
    }

    private boolean adminOrSeller(OAuth2AuthenticatedPrincipal principal, Long userId, Long sellerId) {
        return AuthorityValidator.hasAdminRole(principal)
                || (FormatValidator.equals(userId, sellerId) && AuthorityValidator.hasSellerRole(principal));
    }

    private Map<Long, ProductInfoResult> getProductInfo(List<Post> pagedPosts) {
        List<Long> pricePolicyIds = pagedPosts.stream()
                .filter(post -> PostTargetType.PRICE_POLICY.equals(post.getTargetType()))
                .map(Post::getTargetId)
                .filter(FormatValidator::hasValue)
                .distinct()
                .toList();

        return findProductByPricePolicyPort.findByPricePolicyIds(pricePolicyIds);
    }

    private PostSortProperty resolveSortProperty(GetPostsCommand command) {
        if (command.board().isNonMemberViewBoard()) {
            return PostSortProperty.ORDER_NUM;
        }

        return command.sortProperty() == null ? PostSortProperty.ID : command.sortProperty();
    }

    private String getSortField(PostSortProperty sortProperty) {
        return switch (sortProperty) {
            case ORDER_NUM -> "orderNum";
            case IS_ANSWERED, ID -> "id";
        };
    }

    private boolean matchesKeyword(Post post, PostSearchTarget searchTarget, String searchKeyword) {
        if (searchTarget == PostSearchTarget.TITLE) {
            return containsKeyword(post.getTitle(), searchKeyword);
        }

        if (searchTarget == PostSearchTarget.CONTENT) {
            return containsKeyword(post.getContent(), searchKeyword);
        }

        return containsKeyword(post.getTitle(), searchKeyword) || containsKeyword(post.getContent(), searchKeyword);
    }

    private boolean containsKeyword(String target, String keyword) {
        if (!FormatValidator.hasValue(target)) {
            return false;
        }

        return target.toLowerCase().contains(keyword);
    }

    @Override
    public Post getPost(Long id) {
        return findPostPort.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}
