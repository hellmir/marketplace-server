package com.personal.marketnote.commerce.adapter.out.cache;

import com.personal.marketnote.commerce.port.out.inventory.SaveStockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockRedisAdapter implements SaveStockPort {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(Long pricePolicyId, int stock) {
        String key = buildKey(pricePolicyId);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(stock));
    }

    private String buildKey(Long pricePolicyId) {
        return "pricePolicy:" + pricePolicyId + ":stock";
    }
}
