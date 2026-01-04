package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.product.adapter.in.client.option.response.SelectableProductOptionCategoryResponse;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
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
    private GetFilesResult representativeImages;
    private GetFilesResult contentImages;
    private List<GetProductPricePolicyResult> pricePolicies;

    public static GetProductInfoResponse from(GetProductInfoWithOptionsResult getProductInfoWithOptionsResult) {
        return GetProductInfoResponse.builder()
                .productInfo(getProductInfoWithOptionsResult.productInfo())
                .categories(
                        getProductInfoWithOptionsResult.categories().stream()
                                .map(SelectableProductOptionCategoryResponse::from)
                                .toList()
                )
                .representativeImages(getProductInfoWithOptionsResult.representativeImages())
                .contentImages(getProductInfoWithOptionsResult.contentImages())
                .pricePolicies(getProductInfoWithOptionsResult.pricePolicies())
                .build();
    }
}


