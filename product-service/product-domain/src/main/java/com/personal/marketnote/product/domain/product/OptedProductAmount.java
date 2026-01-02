package com.personal.marketnote.product.domain.product;

import lombok.Getter;

@Getter
public class OptedProductAmount {
    private long totalOptionPrice;
    private long totalOptionPoint;

    public void addAmount(long price, long point) {
        totalOptionPrice += price;
        totalOptionPoint += point;
    }
}
