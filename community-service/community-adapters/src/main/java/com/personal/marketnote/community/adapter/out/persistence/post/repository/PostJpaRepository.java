package com.personal.marketnote.community.adapter.out.persistence.post.repository;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.community.adapter.out.persistence.post.entity.PostJpaEntity;
import com.personal.marketnote.community.domain.post.Board;
import com.personal.marketnote.community.domain.post.PostTargetType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {
    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.parentId IS NULL
              AND (:category IS NULL OR p.category = :category)
              AND (:targetType IS NULL OR p.targetType = :targetType)
              AND (:targetId IS NULL OR p.targetId = :targetId)
              AND (
                    :cursor IS NULL
                    OR (:isDesc = true AND p.id < :cursor)
                    OR (:isDesc = false AND p.id > :cursor)
              )
            """)
    List<PostJpaEntity> findByBoardAndFilters(
            @Param("board") Board board,
            @Param("category") String category,
            @Param("targetType") PostTargetType targetType,
            @Param("targetId") Long targetId,
            @Param("cursor") Long cursor,
            @Param("isDesc") boolean isDesc,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(p)
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.parentId IS NULL
              AND (:category IS NULL OR p.category = :category)
              AND (:targetType IS NULL OR p.targetType = :targetType)
              AND (:targetId IS NULL OR p.targetId = :targetId)
            """)
    long countByBoardAndFilters(
            @Param("board") Board board,
            @Param("category") String category,
            @Param("targetType") PostTargetType targetType,
            @Param("targetId") Long targetId,
            @Param("status") EntityStatus status
    );

    default long countByBoardAndFilters(Board board, String category, PostTargetType targetType, Long targetId) {
        return countByBoardAndFilters(board, category, targetType, targetId, EntityStatus.ACTIVE);
    }

    default List<PostJpaEntity> findByBoardAndFilters(
            Board board, String category, PostTargetType targetType, Long targetId, Long cursor, boolean isDesc, Pageable pageable
    ) {
        return findByBoardAndFilters(board, category, targetType, targetId, cursor, isDesc, EntityStatus.ACTIVE, pageable);
    }

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.userId = :userId
              AND p.parentId IS NULL
              AND (
                    :cursor IS NULL
                    OR (:isDesc = true AND p.id < :cursor)
                    OR (:isDesc = false AND p.id > :cursor)
              )
            """)
    List<PostJpaEntity> findByUserIdAndBoard(
            @Param("userId") Long userId,
            @Param("board") Board board,
            @Param("cursor") Long cursor,
            @Param("isDesc") boolean isDesc,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(p)
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.userId = :userId
              AND p.parentId IS NULL
            """)
    long countByUserIdAndBoard(
            @Param("userId") Long userId,
            @Param("board") Board board,
            @Param("status") EntityStatus status
    );

    default List<PostJpaEntity> findByUserIdAndBoard(
            Long userId, Board board, Long cursor, boolean isDesc, Pageable pageable
    ) {
        return findByUserIdAndBoard(userId, board, cursor, isDesc, EntityStatus.ACTIVE, pageable);
    }

    default long countByUserIdAndBoard(Long userId, Board board) {
        return countByUserIdAndBoard(userId, board, EntityStatus.ACTIVE);
    }

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.parentId IS NULL
              AND (:category IS NULL OR p.category = :category)
              AND (:targetType IS NULL OR p.targetType = :targetType)
              AND (:targetId IS NULL OR p.targetId = :targetId)
              AND (
                    :cursor IS NULL
                    OR (:isDesc = true AND p.id < :cursor)
                    OR (:isDesc = false AND p.id > :cursor)
              )
            ORDER BY
              (CASE WHEN EXISTS (SELECT 1 FROM PostJpaEntity c WHERE c.parentId = p.id AND c.status = :status) THEN 1 ELSE 0 END) DESC,
              p.id DESC
            """)
    List<PostJpaEntity> findByBoardAndFiltersOrderByAnsweredDesc(
            @Param("board") Board board,
            @Param("category") String category,
            @Param("targetType") PostTargetType targetType,
            @Param("targetId") Long targetId,
            @Param("cursor") Long cursor,
            @Param("isDesc") boolean isDesc,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.parentId IS NULL
              AND (:category IS NULL OR p.category = :category)
              AND (:targetType IS NULL OR p.targetType = :targetType)
              AND (:targetId IS NULL OR p.targetId = :targetId)
              AND (
                    :cursor IS NULL
                    OR (:isDesc = true AND p.id < :cursor)
                    OR (:isDesc = false AND p.id > :cursor)
              )
            ORDER BY
              (CASE WHEN EXISTS (SELECT 1 FROM PostJpaEntity c WHERE c.parentId = p.id AND c.status = :status) THEN 1 ELSE 0 END) ASC,
              p.id ASC
            """)
    List<PostJpaEntity> findByBoardAndFiltersOrderByAnsweredAsc(
            @Param("board") Board board,
            @Param("category") String category,
            @Param("targetType") PostTargetType targetType,
            @Param("targetId") Long targetId,
            @Param("cursor") Long cursor,
            @Param("isDesc") boolean isDesc,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    default List<PostJpaEntity> findByBoardAndFiltersOrderByAnswered(
            Board board,
            String category,
            PostTargetType targetType,
            Long targetId,
            Long cursor,
            boolean isDesc,
            Pageable pageable
    ) {
        if (isDesc) {
            return findByBoardAndFiltersOrderByAnsweredDesc(
                    board, category, targetType, targetId, cursor, true, EntityStatus.ACTIVE, pageable
            );
        }

        return findByBoardAndFiltersOrderByAnsweredAsc(
                board, category, targetType, targetId, cursor, false, EntityStatus.ACTIVE, pageable
        );
    }

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.userId = :userId
              AND p.parentId IS NULL
              AND (
                    :cursor IS NULL
                    OR (:isDesc = true AND p.id < :cursor)
                    OR (:isDesc = false AND p.id > :cursor)
              )
            ORDER BY
              (CASE WHEN EXISTS (SELECT 1 FROM PostJpaEntity c WHERE c.parentId = p.id AND c.status = :status) THEN 1 ELSE 0 END) DESC,
              p.id DESC
            """)
    List<PostJpaEntity> findByUserIdAndBoardOrderByAnsweredDesc(
            @Param("userId") Long userId,
            @Param("board") Board board,
            @Param("cursor") Long cursor,
            @Param("isDesc") boolean isDesc,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.userId = :userId
              AND p.parentId IS NULL
              AND (
                    :cursor IS NULL
                    OR (:isDesc = true AND p.id < :cursor)
                    OR (:isDesc = false AND p.id > :cursor)
              )
            ORDER BY
              (CASE WHEN EXISTS (SELECT 1 FROM PostJpaEntity c WHERE c.parentId = p.id AND c.status = :status) THEN 1 ELSE 0 END) ASC,
              p.id ASC
            """)
    List<PostJpaEntity> findByUserIdAndBoardOrderByAnsweredAsc(
            @Param("userId") Long userId,
            @Param("board") Board board,
            @Param("cursor") Long cursor,
            @Param("isDesc") boolean isDesc,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    default List<PostJpaEntity> findByUserIdAndBoardOrderByAnswered(
            Long userId, Board board, Long cursor, boolean isDesc, Pageable pageable
    ) {
        if (isDesc) {
            return findByUserIdAndBoardOrderByAnsweredDesc(userId, board, cursor, true, EntityStatus.ACTIVE, pageable);
        }

        return findByUserIdAndBoardOrderByAnsweredAsc(userId, board, cursor, false, EntityStatus.ACTIVE, pageable);
    }

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.parentId IN :parentIds
              AND p.status = :status
            ORDER BY p.id ASC
            """)
    List<PostJpaEntity> findRepliesByParentIds(
            @Param("parentIds") List<Long> parentIds,
            @Param("status") EntityStatus status
    );

    default List<PostJpaEntity> findRepliesByParentIds(List<Long> parentIds) {
        return findRepliesByParentIds(parentIds, EntityStatus.ACTIVE);
    }
}
