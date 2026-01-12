package com.personal.marketnote.community.domain.post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.utility.NameMasker;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Post {
    private Long id;
    private Long userId;
    private Long parentId;
    private Board board;
    private PostCategory category;
    private PostTargetType targetType;
    private Long targetId;
    private String writerName;
    private String title;
    private String content;
    private Boolean isPrivate;
    private EntityStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;

    private Long orderNum;

    public static Post from(PostCreateState state) {
        return Post.builder()
                .userId(state.getUserId())
                .parentId(state.getParentId())
                .board(state.getBoard())
                .category(PostCategoryResolver.resolve(state.getBoard(), state.getCategory()))
                .targetType(state.getTargetType())
                .targetId(state.getTargetId())
                .writerName(NameMasker.mask(state.getWriterName()))
                .title(state.getTitle())
                .content(state.getContent())
                .isPrivate(Boolean.TRUE.equals(state.getIsPrivate()))
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Post from(PostSnapshotState state) {
        return Post.builder()
                .id(state.getId())
                .userId(state.getUserId())
                .parentId(state.getParentId())
                .board(state.getBoard())
                .category(PostCategoryResolver.resolve(state.getBoard(), state.getCategory()))
                .targetType(state.getTargetType())
                .targetId(state.getTargetId())
                .writerName(state.getWriterName())
                .title(state.getTitle())
                .content(state.getContent())
                .isPrivate(state.getIsPrivate())
                .status(state.getStatus())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .orderNum(state.getOrderNum())
                .build();
    }

    public boolean isActive() {
        return status.isActive();
    }

    public boolean isInactive() {
        return status.isInactive();
    }
}
