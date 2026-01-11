package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.GetMyOrderingProductsCommand;
import com.personal.marketnote.product.port.in.result.product.GetMyOrderProductsResult;

public interface GetMyOrderingProductsUseCase {
    GetMyOrderProductsResult getMyOrderingProducts(GetMyOrderingProductsCommand getMyOrderingProductsCommand);
}
