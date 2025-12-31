package com.personal.marketnote.product.adapter.in.client.category.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.product.adapter.in.client.category.controller.apidocs.GetCategoriesApiDocs;
import com.personal.marketnote.product.adapter.in.client.category.response.GetCategoriesResponse;
import com.personal.marketnote.product.port.in.result.GetCategoriesResult;
import com.personal.marketnote.product.port.in.usecase.category.GetCategoriesUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "카테고리 API", description = "카테고리 관련 API")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final GetCategoriesUseCase getCategoriesUseCase;

    @GetMapping
    @GetCategoriesApiDocs
    public ResponseEntity<BaseResponse<GetCategoriesResponse>> getCategories(
            @RequestParam(value = "parentId", required = false) Long parentCategoryId
    ) {
        GetCategoriesResult result = getCategoriesUseCase.getCategoriesByParentId(parentCategoryId);

        return ResponseEntity.ok(
                BaseResponse.of(
                        GetCategoriesResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "카테고리 목록 조회 성공"
                )
        );
    }
}


