package com.personal.marketnote.community.service.post;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.post.*;
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
        Sort.Direction sortDirection = command.sortDirection() == null ? Sort.Direction.DESC : command.sortDirection();
        PostSortProperty sortProperty = resolveSortProperty(command);
        boolean isDesc = Sort.Direction.DESC.equals(sortDirection);

        Pageable pageable = PageRequest.of(0, command.pageSize() + 1, Sort.by(sortDirection, getSortField(sortProperty)));

        Board board = command.board();
        PostTargetType targetType = command.targetType();

        // 비회원 전용 게시판이거나 상품 상세 정보의 문의 게시판인 경우
        if (board.isNonMemberViewBoard() || FormatValidator.hasValue(targetType)) {
            Posts posts = findPostPort.findPosts(
                    board,
                    command.category(),
                    targetType,
                    command.targetId(),
                    command.cursor(),
                    pageable,
                    isDesc,
                    sortProperty
            );

            return generatePage(command, posts, null);
        }

        // 나의 상품 문의 게시판이거나 나의 1:1 문의 게시판인 경우
        Posts posts = findPostPort.findPosts(
                command.userId(),
                board,
                command.cursor(),
                pageable,
                isDesc,
                sortProperty
        );

        Long totalElements = null;
        if (!FormatValidator.hasValue(command.cursor())) {
            totalElements = findPostPort.count(command.userId(), board);
        }

        return generatePage(command, posts, totalElements);
    }

    private GetPostsResult generatePage(GetPostsCommand command, Posts posts, Long totalElementsOverride) {
        boolean hasNext = posts.size() > command.pageSize();
        List<Post> pagedPosts = hasNext
                ? posts.subList(0, command.pageSize())
                : posts.getPosts();

        Long nextCursor = null;
        if (FormatValidator.hasValue(posts.getPosts())) {
            List<Post> orderedPosts = posts.getPosts();
            nextCursor = orderedPosts.getLast().getId();
        }

        Long totalElements = totalElementsOverride;
        if (totalElements == null && !FormatValidator.hasValue(command.cursor())) {
            totalElements = findPostPort.count(
                    command.board(), command.category(), command.targetType(), command.targetId()
            );
        }

        Map<Long, ProductInfoResult> reviewedProducts = command.board().isProductInquery()
                ? getProductInfo(pagedPosts)
                : Map.of();

        List<PostItemResult> postItems = pagedPosts.stream()
                .map(post -> {
                    if (FormatValidator.hasValue(post.getTargetId())) {
                        return PostItemResult.from(
                                post,
                                PostProductInfoResult.from(reviewedProducts.get(post.getTargetId()))
                        );
                    }

                    return PostItemResult.from(post);
                })
                .toList();

        return GetPostsResult.from(hasNext, nextCursor, totalElements, postItems);
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
}
