package com.personal.marketnote.product.adapter.out.cache;

import com.personal.marketnote.common.domain.exception.illegalargument.numberformat.ParsingIntegerException;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.product.port.out.inventory.FindCacheStockPort;
import com.personal.marketnote.product.port.out.inventory.SaveCacheStockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CacheStockRedisAdapter implements SaveCacheStockPort, FindCacheStockPort {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(Long pricePolicyId, int stock) {
        String key = buildKey(pricePolicyId);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(stock));
    }

    @Override
    public int findByPricePolicyId(Long pricePolicyId) throws ParsingIntegerException {
        return FormatConverter.parseToInteger((
                stringRedisTemplate.opsForValue()
                        .get(buildKey(pricePolicyId)))
        );
    }

    private String buildKey(Long pricePolicyId) {
        return "pricePolicy:" + pricePolicyId + ":stock";
    }
}
