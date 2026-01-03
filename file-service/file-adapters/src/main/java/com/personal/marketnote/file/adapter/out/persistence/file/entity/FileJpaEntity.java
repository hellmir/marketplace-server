package com.personal.marketnote.file.adapter.out.persistence.file.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.file.domain.file.OwnerType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "files")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class FileJpaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 31)
    private OwnerType ownerType;

    @Column(nullable = false)
    private Long ownerId;

    @Column(name = "sort", nullable = false, length = 31)
    private String sort;

    @Column(name = "extension", nullable = false, length = 31)
    private String extension;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "s3_url", nullable = false, length = 511)
    private String s3Url;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public static FileJpaEntity from(com.personal.marketnote.file.domain.file.FileDomain domain, String s3Url) {
        return FileJpaEntity.builder()
                .ownerType(domain.getOwnerType())
                .ownerId(domain.getOwnerId())
                .sort(domain.getSort().name())
                .extension(domain.getExtension())
                .name(domain.getName())
                .s3Url(s3Url)
                .status(EntityStatus.ACTIVE)
                .build();
    }
}
