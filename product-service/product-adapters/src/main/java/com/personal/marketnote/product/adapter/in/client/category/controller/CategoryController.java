package com.personal.marketnote.product.adapter.in.client.category.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.product.adapter.in.client.category.controller.apidocs.GetCategoriesApiDocs;
import com.personal.marketnote.product.adapter.in.client.category.controller.apidocs.RegisterCategoryApiDocs;
import com.personal.marketnote.product.adapter.in.client.category.request.RegisterCategoryRequest;
import com.personal.marketnote.product.adapter.in.client.category.response.GetCategoriesResponse;
import com.personal.marketnote.product.adapter.in.client.category.response.RegisterCategoryResponse;
import com.personal.marketnote.product.adapter.in.client.product.mapper.CategoryRequestToCommandMapper;
import com.personal.marketnote.product.port.in.result.GetCategoriesResult;
import com.personal.marketnote.product.port.in.result.RegisterCategoryResult;
import com.personal.marketnote.product.port.in.usecase.category.GetCategoriesUseCase;
import com.personal.marketnote.product.port.in.usecase.category.RegisterCategoryUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "카테고리 API", description = "카테고리 관련 API")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final GetCategoriesUseCase getCategoriesUseCase;
    private final RegisterCategoryUseCase registerCategoryUseCase;

    @PostMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterCategoryApiDocs
    public ResponseEntity<BaseResponse<RegisterCategoryResponse>> registerCategory(
            @jakarta.validation.Valid @RequestBody RegisterCategoryRequest request
    ) {
        RegisterCategoryResult result = registerCategoryUseCase.registerCategory(
                CategoryRequestToCommandMapper.mapToCommand(request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterCategoryResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "카테고리 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }

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
