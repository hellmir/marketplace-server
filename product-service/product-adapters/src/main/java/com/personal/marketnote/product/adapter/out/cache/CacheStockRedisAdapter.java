package com.personal.marketnote.product.adapter.out.cache;

import com.personal.marketnote.common.domain.exception.illegalargument.numberformat.ParsingIntegerException;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.port.out.inventory.FindCacheStockPort;
import com.personal.marketnote.product.port.out.inventory.SaveCacheStockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
                        .get(buildKey(pricePolicyId))
        ));
    }

    @Override
    public List<Integer> findByPricePolicyIds(List<Long> pricePolicyIds) {
        List<String> stocks = stringRedisTemplate.opsForValue()
                .multiGet(
                        pricePolicyIds.stream()
                                .map(this::buildKey)
                                .toList()
                );

        if (!FormatValidator.hasValue(stocks)) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        for (String stock : stocks) {
            Integer parsedStock = null;
            try {
                parsedStock = FormatConverter.parseToInteger(stock);
            } catch (ParsingIntegerException e) {
            }

            result.add(parsedStock);
        }

        return result;
    }

    private String buildKey(Long pricePolicyId) {
        return "pricePolicy:" + pricePolicyId + ":stock";
    }
}
