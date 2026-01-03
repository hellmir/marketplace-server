package com.personal.marketnote.file.adapter.out.persistence.file.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "resized_file")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ResizedFileJpaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileJpaEntity file;

    @Column(name = "size", nullable = false, length = 255)
    private String size;

    @Column(name = "s3_url", nullable = false, length = 1024)
    private String s3Url;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public static ResizedFileJpaEntity of(FileJpaEntity file, String size) {
        return ResizedFileJpaEntity.builder()
                .file(file)
                .size(size)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static ResizedFileJpaEntity of(FileJpaEntity file, String size, String s3Url) {
        return ResizedFileJpaEntity.builder()
                .file(file)
                .size(size)
                .s3Url(s3Url)
                .status(EntityStatus.ACTIVE)
                .build();
    }
}


