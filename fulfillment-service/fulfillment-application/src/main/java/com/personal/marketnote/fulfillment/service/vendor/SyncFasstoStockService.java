package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.fulfillment.domain.FasstoAccessToken;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStockDetailCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.SyncFasstoStockCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoStockInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoStockDetailUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RequestFasstoAuthUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.SyncFasstoStockUseCase;
import com.personal.marketnote.fulfillment.port.out.commerce.UpdateCommerceInventoryCommand;
import com.personal.marketnote.fulfillment.port.out.commerce.UpdateCommerceInventoryItemCommand;
import com.personal.marketnote.fulfillment.port.out.commerce.UpdateCommerceInventoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class SyncFasstoStockService implements SyncFasstoStockUseCase {
    private static final String OUT_OF_STOCK_INCLUDED = "Y";

    private final RequestFasstoAuthUseCase requestFasstoAuthUseCase;
    private final GetFasstoStockDetailUseCase getFasstoStockDetailUseCase;
    private final UpdateCommerceInventoryPort updateCommerceInventoryPort;

    @Override
    public void sync(SyncFasstoStockCommand command) {
        if (FormatValidator.hasNoValue(command)) {
            throw new IllegalArgumentException("Sync Fassto stock command is required.");
        }
        if (FormatValidator.hasNoValue(command.customerCode())) {
            throw new IllegalArgumentException("Customer code is required for Fassto stock sync.");
        }
        if (FormatValidator.hasNoValue(command.productIds())) {
            throw new IllegalArgumentException("Product ids are required for Fassto stock sync.");
        }

        List<Long> productIds = command.productIds().stream()
                .filter(productId -> FormatValidator.hasValue(productId))
                .distinct()
                .toList();
        if (FormatValidator.hasNoValue(productIds)) {
            throw new IllegalArgumentException("Valid product ids are required for Fassto stock sync.");
        }

        FasstoAccessToken accessToken = requestFasstoAuthUseCase.requestAccessToken();
        if (FormatValidator.hasNoValue(accessToken) || FormatValidator.hasNoValue(accessToken.getValue())) {
            throw new IllegalArgumentException("Fassto access token is required for stock sync.");
        }

        List<UpdateCommerceInventoryItemCommand> inventories = new ArrayList<>();
        for (Long productId : productIds) {
            GetFasstoStocksResult stockDetail = getFasstoStockDetailUseCase.getStockDetail(
                    GetFasstoStockDetailCommand.of(
                            command.customerCode(),
                            accessToken.getValue(),
                            String.valueOf(productId),
                            OUT_OF_STOCK_INCLUDED
                    )
            );
            int stockQuantity = resolveStockQuantity(stockDetail);
            inventories.add(UpdateCommerceInventoryItemCommand.of(productId, stockQuantity));
        }

        updateCommerceInventoryPort.updateInventories(UpdateCommerceInventoryCommand.of(inventories));
    }

    private int resolveStockQuantity(GetFasstoStocksResult result) {
        if (FormatValidator.hasNoValue(result) || FormatValidator.hasNoValue(result.stocks())) {
            return 0;
        }

        int totalStock = 0;
        for (FasstoStockInfoResult stockInfo : result.stocks()) {
            if (FormatValidator.hasNoValue(stockInfo)) {
                continue;
            }
            Integer quantity = resolveAvailableQuantity(stockInfo);
            if (FormatValidator.hasValue(quantity)) {
                totalStock += Math.max(0, quantity);
            }
        }
        return totalStock;
    }

    private Integer resolveAvailableQuantity(FasstoStockInfoResult stockInfo) {
        if (FormatValidator.hasNoValue(stockInfo)) {
            return null;
        }

        Integer canStockQty = stockInfo.canStockQty();
        if (FormatValidator.hasValue(canStockQty)) {
            return canStockQty;
        }
        return stockInfo.stockQty();
    }
}
