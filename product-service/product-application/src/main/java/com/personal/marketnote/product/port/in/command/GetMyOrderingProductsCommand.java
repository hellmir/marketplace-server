package com.personal.marketnote.product.port.in.command;

import java.util.List;

public record GetMyOrderingProductsCommand(
        List<OrderingItemCommand> orderingItemCommands
) {
    public static GetMyOrderingProductsCommand from(List<OrderingItemCommand> commands) {
        return new GetMyOrderingProductsCommand(commands);
    }
}
