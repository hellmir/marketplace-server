package com.personal.marketnote.community.adapter.out.persistence.post;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.adapter.out.mapper.PostJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.post.entity.PostJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.post.repository.PostJpaRepository;
import com.personal.marketnote.community.domain.post.*;
import com.personal.marketnote.community.port.out.post.FindPostPort;
import com.personal.marketnote.community.port.out.post.SavePostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostPersistenceAdapter implements SavePostPort, FindPostPort {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        PostJpaEntity savedEntity = postJpaRepository.save(PostJpaEntity.from(post));
        savedEntity.setIdToOrderNum();

        return PostJpaEntityToDomainMapper.mapToDomain(savedEntity).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return postJpaRepository.existsById(id);
    }

    @Override
    public Posts findPosts(
            Board board,
            String category,
            PostTargetType targetType,
            Long targetId,
            Long cursor,
            Pageable pageable,
            boolean isDesc,
            PostSortProperty sortProperty,
            Long viewerId,
            PostFilterCategory filter,
            PostFilterValue filterValue
    ) {
        String effectiveCategory = category;
        boolean isPublicOnly = false;
        Long filterUserId = null;

        if (filter == PostFilterCategory.FAQ_CATEGORY && FormatValidator.hasValue(filterValue)) {
            effectiveCategory = filterValue.name();
        }

        if (
                filter == PostFilterCategory.IS_PUBLIC
                        && FormatValidator.hasValue(filterValue)
                        && FormatValidator.equals(filterValue, PostFilterValue.TRUE)
        ) {
            isPublicOnly = true;
        }

        if (
                filter == PostFilterCategory.IS_MINE
                        && FormatValidator.hasValue(viewerId)
                        && FormatValidator.equals(filterValue, PostFilterValue.TRUE)
        ) {
            filterUserId = viewerId;
        }

        if (FormatValidator.equals(sortProperty, PostSortProperty.IS_ANSWERED)) {
            return mapToPostsWithReplies(
                    postJpaRepository.findByBoardAndFiltersOrderByAnswered(
                            board,
                            effectiveCategory,
                            targetType,
                            targetId,
                            cursor,
                            isDesc,
                            isPublicOnly,
                            filterUserId,
                            pageable
                    )
            );
        }

        return mapToPostsWithReplies(
                postJpaRepository.findByBoardAndFilters(
                        board,
                        effectiveCategory,
                        targetType,
                        targetId,
                        cursor,
                        isDesc,
                        isPublicOnly,
                        filterUserId,
                        EntityStatus.ACTIVE,
                        pageable
                )
        );
    }

    @Override
    public Posts findUserPosts(Long userId, Board board, Long cursor, Pageable pageable, boolean isDesc, PostSortProperty sortProperty) {
        if (FormatValidator.equals(sortProperty, PostSortProperty.IS_ANSWERED)) {
            return mapToPostsWithReplies(
                    postJpaRepository.findByUserIdAndBoardOrderByAnswered(userId, board, cursor, isDesc, pageable)
            );
        }

        return mapToPostsWithReplies(
                postJpaRepository.findByUserIdAndBoard(userId, board, cursor, isDesc, pageable)
        );
    }

    @Override
    public long count(
            Board board,
            String category,
            PostTargetType targetType,
            Long targetId,
            Long viewerId,
            PostFilterCategory filter,
            PostFilterValue filterValue
    ) {
        String effectiveCategory = category;
        boolean isPublicOnly = false;
        Long filterUserId = null;

        if (filter == PostFilterCategory.FAQ_CATEGORY && FormatValidator.hasValue(filterValue)) {
            effectiveCategory = filterValue.name();
        }

        if (
                filter == PostFilterCategory.IS_PUBLIC
                        && FormatValidator.hasValue(filterValue)
                        && FormatValidator.equals(filterValue, PostFilterValue.TRUE)
        ) {
            isPublicOnly = true;
        }

        if (
                filter == PostFilterCategory.IS_MINE
                        && FormatValidator.hasValue(viewerId)
                        && FormatValidator.equals(filterValue, PostFilterValue.TRUE)
        ) {
            filterUserId = viewerId;
        }

        return postJpaRepository.countByBoardAndFilters(
                board, effectiveCategory, targetType, targetId, isPublicOnly, filterUserId
        );
    }

    @Override
    public long count(Long userId, Board board) {
        return postJpaRepository.countByUserIdAndBoard(userId, board);
    }

    private Posts mapToPostsWithReplies(List<PostJpaEntity> parentEntities) {
        List<Post> parents = parentEntities.stream()
                .map(PostJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if (!FormatValidator.hasValue(parents)) {
            return Posts.from(parents);
        }

        List<Long> parentIds = parents.stream()
                .map(Post::getId)
                .toList();

        Map<Long, List<Post>> repliesByParentId = postJpaRepository.findRepliesByParentIds(parentIds)
                .stream()
                .map(PostJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(Post::getParentId));

        parents.forEach(parent ->
                parent.updateReplies(repliesByParentId.getOrDefault(parent.getId(), Collections.emptyList()))
        );

        return Posts.from(parents);
    }
}
