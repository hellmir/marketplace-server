package com.personal.marketnote.product.adapter.out.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.port.out.file.FindProductCatalogImagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class FileServiceClient implements FindProductCatalogImagePort {

    @Value("${file-service.base-url:http://localhost:9000}")
    private String fileServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<GetFilesResult> findCatalogImagesByProductId(Long productId) {
        return findImagesByProductIdAndSort(productId, "PRODUCT_CATALOG_IMAGE");
    }

    @Override
    public Optional<GetFilesResult> findImagesByProductIdAndSort(Long productId, String sort) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(fileServiceBaseUrl)
                    .path("/api/v1/images")
                    .queryParam("ownerType", "PRODUCT")
                    .queryParam("ownerId", productId)
                    .queryParam("sort", sort)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(adminAccessToken);
            HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<BaseResponse<FilesContent>> response =
                    restTemplate.exchange(
                            uri,
                            org.springframework.http.HttpMethod.GET,
                            httpEntity,
                            new ParameterizedTypeReference<BaseResponse<FilesContent>>() {
                            }
                    );

            BaseResponse<FilesContent> body = response.getBody();
            FilesContent content = FormatValidator.hasValue(body)
                    ? body.content
                    : null;

            if (!FormatValidator.hasValue(content) || !FormatValidator.hasValue(content.files)) {
                return Optional.empty();
            }

            List<GetFilesResult.FileItem> items = content.files.stream()
                    .map(f -> new GetFilesResult.FileItem(
                            f.id,
                            f.sort,
                            f.extension,
                            f.name,
                            f.s3Url,
                            f.resizedS3Urls,
                            f.orderNum
                    ))
                    .toList();

            return Optional.of(new GetFilesResult(items));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
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
