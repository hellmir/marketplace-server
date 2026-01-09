package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.commerce.port.out.result.product.GetOrderedProductResult;
import com.personal.marketnote.common.utility.FormatValidator;

public record GetOrderProductResult(
        Long pricePolicyId,
        Integer quantity,
        Long unitAmount,
        String imageUrl,
        OrderStatus orderStatus,
        Long productId,
        String productName
) {
    public static GetOrderProductResult from(OrderProduct orderProduct) {
        return from(orderProduct, null);
    }

    public static GetOrderProductResult from(
            OrderProduct orderProduct,
            GetOrderedProductResult productSummary
    ) {
        Long productId = FormatValidator.hasValue(productSummary) ? productSummary.productId() : null;
        String productName = FormatValidator.hasValue(productSummary) ? productSummary.name() : null;

        return new GetOrderProductResult(
                orderProduct.getPricePolicyId(),
                orderProduct.getQuantity(),
                orderProduct.getUnitAmount(),
                orderProduct.getImageUrl(),
                orderProduct.getOrderStatus(),
                productId,
                productName
        );
    }
}
