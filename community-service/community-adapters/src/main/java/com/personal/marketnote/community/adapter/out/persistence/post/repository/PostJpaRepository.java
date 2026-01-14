package com.personal.marketnote.community.adapter.out.persistence.post.repository;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.community.adapter.out.persistence.post.entity.PostJpaEntity;
import com.personal.marketnote.community.domain.post.Board;
import com.personal.marketnote.community.domain.post.PostSearchKeywordCategory;
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
                :isPublicOnly IS NULL
                OR (:isPublicOnly = true AND p.isPrivate = false)
                OR (:isPublicOnly = false)
              )
              AND (:filterUserId IS NULL OR p.userId = :filterUserId)
              AND (
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
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
            @Param("isPublicOnly") Boolean isPublicOnly,
            @Param("filterUserId") Long filterUserId,
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    default List<PostJpaEntity> findByBoardAndFilters(
            Board board,
            String category,
            PostTargetType targetType,
            Long targetId,
            Long cursor,
            boolean isDesc,
            Boolean isPublicOnly,
            Long filterUserId,
            String keyword,
            PostSearchKeywordCategory searchKeywordCategory,
            Pageable pageable
    ) {
        return findByBoardAndFilters(
                board, category, targetType, targetId, cursor, isDesc, isPublicOnly, filterUserId, keyword, searchKeywordCategory, EntityStatus.ACTIVE, pageable
        );
    }

    @Query("""
            SELECT COUNT(p)
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.parentId IS NULL
              AND (:category IS NULL OR p.category = :category)
              AND (:targetType IS NULL OR p.targetType = :targetType)
              AND (:targetId IS NULL OR p.targetId = :targetId)
              AND (
                :isPublicOnly IS NULL
                OR (:isPublicOnly = true AND p.isPrivate = false)
                OR (:isPublicOnly = false)
              )
              AND (:filterUserId IS NULL OR p.userId = :filterUserId)
              AND (
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
            """)
    long countByBoardAndFilters(
            @Param("board") Board board,
            @Param("category") String category,
            @Param("targetType") PostTargetType targetType,
            @Param("targetId") Long targetId,
            @Param("isPublicOnly") Boolean isPublicOnly,
            @Param("filterUserId") Long filterUserId,
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
            @Param("status") EntityStatus status
    );

    default long countByBoardAndFilters(
            Board board,
            String category,
            PostTargetType targetType,
            Long targetId,
            Boolean isPublicOnly,
            Long filterUserId,
            String keyword,
            PostSearchKeywordCategory searchKeywordCategory
    ) {
        return countByBoardAndFilters(board, category, targetType, targetId, isPublicOnly, filterUserId, keyword, searchKeywordCategory, EntityStatus.ACTIVE);
    }

    @Query("""
            SELECT p
            FROM PostJpaEntity p
            WHERE p.status = :status
              AND p.board = :board
              AND p.userId = :userId
              AND p.parentId IS NULL
              AND (
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
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
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
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
              AND (
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
            """)
    long countByUserIdAndBoard(
            @Param("userId") Long userId,
            @Param("board") Board board,
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
            @Param("status") EntityStatus status
    );

    default List<PostJpaEntity> findByUserIdAndBoard(
            Long userId, Board board, Long cursor, boolean isDesc, String keyword, PostSearchKeywordCategory searchKeywordCategory, Pageable pageable
    ) {
        return findByUserIdAndBoard(userId, board, cursor, isDesc, keyword, searchKeywordCategory, EntityStatus.ACTIVE, pageable);
    }

    default long countByUserIdAndBoard(Long userId, Board board, String keyword, PostSearchKeywordCategory searchKeywordCategory) {
        return countByUserIdAndBoard(userId, board, keyword, searchKeywordCategory, EntityStatus.ACTIVE);
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
                :isPublicOnly IS NULL
                OR (:isPublicOnly = true AND p.isPrivate = false)
                OR (:isPublicOnly = false)
              )
              AND (:filterUserId IS NULL OR p.userId = :filterUserId)
              AND (
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
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
            @Param("isPublicOnly") Boolean isPublicOnly,
            @Param("filterUserId") Long filterUserId,
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
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
                :isPublicOnly IS NULL
                OR (:isPublicOnly = true AND p.isPrivate = false)
                OR (:isPublicOnly = false)
              )
              AND (:filterUserId IS NULL OR p.userId = :filterUserId)
              AND (
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
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
            @Param("isPublicOnly") Boolean isPublicOnly,
            @Param("filterUserId") Long filterUserId,
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
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
            Boolean isPublicOnly,
            Long filterUserId,
            String keyword,
            PostSearchKeywordCategory searchKeywordCategory,
            Pageable pageable
    ) {
        if (isDesc) {
            return findByBoardAndFiltersOrderByAnsweredDesc(
                    board, category, targetType, targetId, cursor, true, isPublicOnly, filterUserId, keyword, searchKeywordCategory, EntityStatus.ACTIVE, pageable
            );
        }

        return findByBoardAndFiltersOrderByAnsweredAsc(
                board, category, targetType, targetId, cursor, false, isPublicOnly, filterUserId, keyword, searchKeywordCategory, EntityStatus.ACTIVE, pageable
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
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
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
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
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
                    :keyword IS NULL
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.TITLE)
                        AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                 OR (
                        (:searchKeywordCategory IS NULL OR :searchKeywordCategory = com.personal.marketnote.community.domain.post.PostSearchKeywordCategory.CONTENT)
                        AND LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
              )
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
            @Param("keyword") String keyword,
            @Param("searchKeywordCategory") PostSearchKeywordCategory searchKeywordCategory,
            @Param("status") EntityStatus status,
            Pageable pageable
    );

    default List<PostJpaEntity> findByUserIdAndBoardOrderByAnswered(
            Long userId,
            Board board,
            Long cursor,
            boolean isDesc,
            String keyword,
            PostSearchKeywordCategory searchKeywordCategory,
            Pageable pageable
    ) {
        if (isDesc) {
            return findByUserIdAndBoardOrderByAnsweredDesc(
                    userId, board, cursor, true, keyword, searchKeywordCategory, EntityStatus.ACTIVE, pageable
            );
        }

        return findByUserIdAndBoardOrderByAnsweredAsc(
                userId, board, cursor, false, keyword, searchKeywordCategory, EntityStatus.ACTIVE, pageable
        );
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
