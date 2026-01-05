package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.product.adapter.in.client.option.response.SelectableProductOptionCategoryResponse;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyWithOptionsResult;
import com.personal.marketnote.product.port.in.result.product.GetProductInfoResult;
import com.personal.marketnote.product.port.in.result.product.GetProductInfoWithOptionsResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetProductInfoResponse {
    private GetProductInfoResult productInfo;
    private List<SelectableProductOptionCategoryResponse> categories;
    private List<GetFileResult> representativeImages;
    private List<GetFileResult> contentImages;
    private List<GetProductPricePolicyWithOptionsResult> pricePolicies;

    public static GetProductInfoResponse from(GetProductInfoWithOptionsResult getProductInfoWithOptionsResult) {
        return GetProductInfoResponse.builder()
                .productInfo(getProductInfoWithOptionsResult.productInfo())
                .categories(
                        getProductInfoWithOptionsResult.categories().stream()
                                .map(SelectableProductOptionCategoryResponse::from)
                                .toList()
                )
                .representativeImages(getProductInfoWithOptionsResult.representativeImages().images())
                .contentImages(getProductInfoWithOptionsResult.contentImages().images())
                .pricePolicies(getProductInfoWithOptionsResult.pricePolicies())
                .build();
    }
}


