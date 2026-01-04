package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

import java.util.Optional;

public class PricePolicyJpaEntityToDomainMapper {
    public static Optional<PricePolicy> mapToDomain(PricePolicyJpaEntity pricePolicyJpaEntity) {
        return Optional.ofNullable(pricePolicyJpaEntity)
                .map(
                        entity -> PricePolicy.of(
                                pricePolicyJpaEntity.getId(),
                                ProductJpaEntityToDomainMapper.mapToDomain(entity.getProductJpaEntity()).orElse(null),
                                entity.getPrice(),
                                entity.getDiscountPrice(),
                                entity.getAccumulationRate(),
                                entity.getAccumulatedPoint(),
                                entity.getDiscountRate()
                        )
                );
    }
}
