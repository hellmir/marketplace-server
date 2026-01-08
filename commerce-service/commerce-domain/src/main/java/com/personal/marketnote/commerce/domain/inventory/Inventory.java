package com.personal.marketnote.commerce.domain.inventory;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Inventory {
    private Long pricePolicyId;
    private Stock stock;

    public static Inventory of(Long pricePolicyId) {
        return Inventory.builder()
                .pricePolicyId(pricePolicyId)
                .stock(Stock.of(ZERO.toString()))
                .build();
    }

    public static Inventory of(Long pricePolicyId, Integer stock) {
        return Inventory.builder()
                .pricePolicyId(pricePolicyId)
                .stock(Stock.of(stock.toString()))
                .build();
    }

    public void reduce(int stockToReduce) {
        Integer reducedStock = stock.reduce(stockToReduce);
        stock = Stock.of(reducedStock.toString());
    }

    public Integer getStockValue() {
        return stock.getValue();
    }

    public boolean isMe(Long pricePolicyId) {
        return FormatValidator.equals(this.pricePolicyId, pricePolicyId);
    }
}
