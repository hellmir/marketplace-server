package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.port.in.result.fulfillment.FulfillmentVendorGoodsInfoResult;
import com.personal.marketnote.product.port.in.result.fulfillment.GetFulfillmentVendorGoodsResult;
import com.personal.marketnote.product.port.in.result.product.AdminProductItemResult;
import com.personal.marketnote.product.port.in.result.product.GetAdminProductsResult;
import com.personal.marketnote.product.port.in.result.product.GetProductsResult;
import com.personal.marketnote.product.port.in.usecase.product.GetAdminProductsUseCase;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.fulfillment.GetFulfillmentVendorGoodsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetAdminProductsService implements GetAdminProductsUseCase {
    private final GetProductUseCase getProductUseCase;
    private final GetFulfillmentVendorGoodsPort getFulfillmentVendorGoodsPort;

    @Override
    public GetAdminProductsResult getAdminProducts(
            Long categoryId,
            List<Long> pricePolicyIds,
            Long cursor,
            int pageSize,
            Sort.Direction sortDirection,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        GetProductsResult products = getProductUseCase.getProducts(
                categoryId,
                pricePolicyIds,
                cursor,
                pageSize,
                sortDirection,
                sortProperty,
                searchTarget,
                searchKeyword
        );
        GetFulfillmentVendorGoodsResult fulfillmentGoods = getFulfillmentVendorGoodsPort.getFulfillmentVendorGoods();
        Map<String, FulfillmentVendorGoodsInfoResult> goodsByCstGodCd = mapGoodsByCstGodCd(fulfillmentGoods);
        List<AdminProductItemResult> items = products.products().stream()
                .map(item -> AdminProductItemResult.of(
                        item,
                        FormatValidator.hasValue(item.getId())
                                ? goodsByCstGodCd.get(String.valueOf(item.getId()))
                                : null
                ))
                .toList();

        return GetAdminProductsResult.of(
                products.totalElements(),
                products.nextCursor(),
                products.hasNext(),
                items
        );
    }

    private Map<String, FulfillmentVendorGoodsInfoResult> mapGoodsByCstGodCd(
            GetFulfillmentVendorGoodsResult fulfillmentGoods
    ) {
        if (FormatValidator.hasNoValue(fulfillmentGoods)
                || FormatValidator.hasNoValue(fulfillmentGoods.goods())) {
            return Map.of();
        }

        Map<String, FulfillmentVendorGoodsInfoResult> goodsByCstGodCd = new HashMap<>();
        for (FulfillmentVendorGoodsInfoResult item : fulfillmentGoods.goods()) {
            if (FormatValidator.hasNoValue(item) || FormatValidator.hasNoValue(item.cstGodCd())) {
                continue;
            }
            goodsByCstGodCd.putIfAbsent(item.cstGodCd(), item);
        }
        return goodsByCstGodCd;
    }
}
