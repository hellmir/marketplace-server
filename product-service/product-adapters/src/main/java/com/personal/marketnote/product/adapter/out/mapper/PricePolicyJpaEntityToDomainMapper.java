package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

import java.util.List;
import java.util.Optional;

public class PricePolicyJpaEntityToDomainMapper {
    public static Optional<PricePolicy> mapToDomain(PricePolicyJpaEntity pricePolicyJpaEntity) {
        return Optional.ofNullable(pricePolicyJpaEntity)
                .map(
                        entity -> PricePolicy.of(
                                pricePolicyJpaEntity.getId(),
                                entity.getPrice(),
                                entity.getDiscountPrice(),
                                entity.getAccumulationRate(),
                                entity.getAccumulatedPoint(),
                                entity.getDiscountRate()
                        )
                );
    }

    public static Optional<PricePolicy> mapToDomain(PricePolicyJpaEntity pricePolicyJpaEntity, List<Long> optionIds) {
        return Optional.ofNullable(pricePolicyJpaEntity)
                .map(
                        entity -> {
                            PricePolicy pricePolicy = PricePolicy.of(
                                    pricePolicyJpaEntity.getId(),
                                    pricePolicyJpaEntity.getPrice(),
                                    pricePolicyJpaEntity.getDiscountPrice(),
                                    pricePolicyJpaEntity.getDiscountRate(),
                                    pricePolicyJpaEntity.getAccumulatedPoint(),
                                    pricePolicyJpaEntity.getAccumulationRate(),
                                    optionIds
                            );
                            pricePolicy.addProduct(
                                    ProductJpaEntityToDomainMapper.mapToDomain(entity.getProductJpaEntity())
                                            .orElse(null)
                            );

                            return pricePolicy;
                        }
                );
    }
}
