package com.personal.marketnote.community.adapter.out.persistence.post;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.community.adapter.out.mapper.PostJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.post.entity.PostJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.post.repository.PostJpaRepository;
import com.personal.marketnote.community.domain.post.*;
import com.personal.marketnote.community.port.out.post.FindPostPort;
import com.personal.marketnote.community.port.out.post.SavePostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

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
            PostSortProperty sortProperty
    ) {
        if (PostSortProperty.IS_ANSWERED.equals(sortProperty)) {
            return Posts.from(
                    postJpaRepository.findByBoardAndFiltersOrderByAnswered(
                                    board, category, targetType, targetId, cursor, isDesc, pageable
                            ).stream()
                            .map(entity -> PostJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                            .filter(Objects::nonNull)
                            .toList()
            );
        }

        return Posts.from(
                postJpaRepository.findByBoardAndFilters(board, category, targetType, targetId, cursor, isDesc, pageable)
                        .stream()
                        .map(entity -> PostJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    @Override
    public Posts findPosts(Long userId, Board board, Long cursor, Pageable pageable, boolean isDesc, PostSortProperty sortProperty) {
        if (PostSortProperty.IS_ANSWERED.equals(sortProperty)) {
            return Posts.from(
                    postJpaRepository.findByUserIdAndBoardOrderByAnswered(userId, board, cursor, isDesc, pageable)
                            .stream()
                            .map(entity -> PostJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                            .filter(Objects::nonNull)
                            .toList()
            );
        }

        return Posts.from(
                postJpaRepository.findByUserIdAndBoard(userId, board, cursor, isDesc, pageable)
                        .stream()
                        .map(entity -> PostJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    @Override
    public long count(Board board, String category, PostTargetType targetType, Long targetId) {
        return postJpaRepository.countByBoardAndFilters(board, category, targetType, targetId);
    }

    @Override
    public long count(Long userId, Board board) {
        return postJpaRepository.countByUserIdAndBoard(userId, board);
    }
}
