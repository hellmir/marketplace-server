package com.personal.marketnote.product.adapter.in.web.product.mapper;

import com.personal.marketnote.product.adapter.in.web.cart.request.GetMyOrderingProductsRequest;
import com.personal.marketnote.product.adapter.in.web.category.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.web.option.request.UpdateProductOptionsRequest;
import com.personal.marketnote.product.adapter.in.web.product.request.RegisterProductFulfillmentVendorGoodsRequest;
import com.personal.marketnote.product.adapter.in.web.product.request.RegisterProductRequest;
import com.personal.marketnote.product.adapter.in.web.product.request.UpdateProductRequest;
import com.personal.marketnote.product.port.in.command.*;

import java.util.List;

public class ProductRequestToCommandMapper {
    public static RegisterProductCommand mapToCommand(RegisterProductRequest registerProductRequest) {
        return RegisterProductCommand.builder()
                .sellerId(registerProductRequest.getSellerId())
                .name(registerProductRequest.getName())
                .brandName(registerProductRequest.getBrandName())
                .detail(registerProductRequest.getDetail())
                .price(registerProductRequest.getPrice())
                .discountPrice(registerProductRequest.getDiscountPrice())
                .accumulatedPoint(registerProductRequest.getAccumulatedPoint())
                .isFindAllOptions(registerProductRequest.getIsFindAllOptions())
                .tags(registerProductRequest.getTags())
                .fulfillmentVendorGoods(mapToCommand(registerProductRequest.getFulfillmentVendorGoods()))
                .build();
    }

    private static FulfillmentVendorGoodsOptionCommand mapToCommand(RegisterProductFulfillmentVendorGoodsRequest request) {
        if (request == null) {
            return null;
        }

        return FulfillmentVendorGoodsOptionCommand.builder()
                .godType(request.getGodType())
                .giftDiv(request.getGiftDiv())
                .godOptCd1(request.getGodOptCd1())
                .godOptCd2(request.getGodOptCd2())
                .invGodNmUseYn(request.getInvGodNmUseYn())
                .invGodNm(request.getInvGodNm())
                .supCd(request.getSupCd())
                .cateCd(request.getCateCd())
                .seasonCd(request.getSeasonCd())
                .genderCd(request.getGenderCd())
                .makeYr(request.getMakeYr())
                .godPr(request.getGodPr())
                .inPr(request.getInPr())
                .salPr(request.getSalPr())
                .dealTemp(request.getDealTemp())
                .pickFac(request.getPickFac())
                .godBarcd(request.getGodBarcd())
                .boxWeight(request.getBoxWeight())
                .origin(request.getOrigin())
                .distTermMgtYn(request.getDistTermMgtYn())
                .useTermDay(request.getUseTermDay())
                .outCanDay(request.getOutCanDay())
                .inCanDay(request.getInCanDay())
                .boxDiv(request.getBoxDiv())
                .bufGodYn(request.getBufGodYn())
                .loadingDirection(request.getLoadingDirection())
                .subMate(request.getSubMate())
                .useYn(request.getUseYn())
                .safetyStock(request.getSafetyStock())
                .feeYn(request.getFeeYn())
                .saleUnitQty(request.getSaleUnitQty())
                .cstGodImgUrl(request.getCstGodImgUrl())
                .externalGodImgUrl(request.getExternalGodImgUrl())
                .build();
    }

    public static RegisterProductCategoriesCommand mapToCommand(
            Long productId, RegisterProductCategoriesRequest registerProductCategoriesRequest
    ) {
        return RegisterProductCategoriesCommand.of(
                productId, registerProductCategoriesRequest.getCategoryIds()
        );
    }

    public static RegisterProductOptionsCommand mapToCommand(
            Long productId, UpdateProductOptionsRequest request
    ) {
        List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(o.getContent()))
                .toList();
        return RegisterProductOptionsCommand.of(productId, request.getCategoryName(), optionItems);
    }

    public static UpdateProductOptionsCommand mapToUpdateCommand(
            Long productId, Long optionCategoryId, UpdateProductOptionsRequest request
    ) {
        List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(o.getContent()))
                .toList();
        return UpdateProductOptionsCommand.of(
                productId, optionCategoryId, request.getCategoryName(), optionItems
        );
    }

    public static UpdateProductCommand mapToCommand(
            Long id, UpdateProductRequest request
    ) {
        return UpdateProductCommand.builder()
                .id(id)
                .name(request.getName())
                .brandName(request.getBrandName())
                .detail(request.getDetail())
                .isFindAllOptions(request.getIsFindAllOptions())
                .tags(request.getTags())
                .build();
    }

    public static GetMyOrderingProductsQuery mapToCommand(
            GetMyOrderingProductsRequest getMyOrderingProductsRequest
    ) {
        return GetMyOrderingProductsQuery.from(
                getMyOrderingProductsRequest.orderingItemRequests()
                        .stream()
                        .map(request -> OrderingItemQuery.of(
                                request.pricePolicyId(), request.sharerId(), request.quantity(), request.imageUrl())
                        )
                        .toList()
        );
    }
}
