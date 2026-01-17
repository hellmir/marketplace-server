package com.personal.marketnote.community.adapter.out.service.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.port.out.file.FindPostImagesPort;
import com.personal.marketnote.community.port.out.file.FindReviewImagesPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.personal.marketnote.common.domain.file.OwnerType.POST;
import static com.personal.marketnote.common.domain.file.OwnerType.REVIEW;
import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_MAX_REQUEST_COUNT;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class FileServiceClient implements FindReviewImagesPort, FindPostImagesPort {
    @Value("${file-service.base-url:http://localhost:9000}")
    private String fileServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public Optional<GetFilesResult> findImagesByReviewIdAndSort(Long reviewId, FileSort sort) {
        return findImagesByOwnerAndSort(REVIEW, reviewId, sort);
    }

    @Override
    public Optional<GetFilesResult> findImagesByPostIdAndSort(Long postId, FileSort sort) {
        return findImagesByOwnerAndSort(POST, postId, sort);
    }

    private Optional<GetFilesResult> findImagesByOwnerAndSort(OwnerType ownerType, Long ownerId, FileSort sort) {
        URI uri = UriComponentsBuilder
                .fromUriString(fileServiceBaseUrl)
                .path("/api/v1/files")
                .queryParam("ownerType", ownerType)
                .queryParam("ownerId", ownerId)
                .queryParam("sort", sort)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        return sendRequest(uri, httpEntity, ownerId, sort, ownerType);
    }

    private Optional<GetFilesResult> sendRequest(
            URI uri, HttpEntity<Void> httpEntity, Long ownerId, FileSort sort, OwnerType ownerType
    ) {
        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                ResponseEntity<BaseResponse<FilesContent>> response =
                        restTemplate.exchange(
                                uri,
                                HttpMethod.GET,
                                httpEntity,
                                new ParameterizedTypeReference<>() {
                                }
                        );

                BaseResponse<FilesContent> body = response.getBody();
                FilesContent filesContent = null;
                if (FormatValidator.hasValue(body)) {
                    filesContent = body.content;
                }

                if (!FormatValidator.hasValue(filesContent) || !FormatValidator.hasValue(filesContent.files)) {
                    return Optional.empty();
                }

                List<GetFileResult> getFileResults = filesContent.files
                        .stream()
                        .map(getFileResult -> new GetFileResult(
                                getFileResult.id,
                                getFileResult.sort,
                                getFileResult.extension,
                                getFileResult.name,
                                getFileResult.s3Url,
                                getFileResult.resizedS3Urls,
                                getFileResult.orderNum
                        ))
                        .toList();

                return Optional.of(new GetFilesResult(getFileResults));
            } catch (Exception e) {
                log.warn(e.getMessage(), e);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        log.error("Failed to find images by owner type: {} id: {} and sort: {}", ownerType, ownerId, sort);
        return Optional.empty();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class BaseResponse<T> {
        @JsonProperty("content")
        T content;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FilesContent {
        @JsonProperty("files")
        List<FileItem> files;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FileItem {
        @JsonProperty("id")
        Long id;

        @JsonProperty("sort")
        String sort;

        @JsonProperty("extension")
        String extension;

        @JsonProperty("name")
        String name;

        @JsonProperty("s3Url")
        String s3Url;

        @JsonProperty("resizedS3Urls")
        List<String> resizedS3Urls;

        @JsonProperty("orderNum")
        Long orderNum;
    }
}
