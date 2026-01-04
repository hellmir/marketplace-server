package com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository;

import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PricePolicyJpaRepository extends JpaRepository<PricePolicyJpaEntity, Long> {
    boolean existsByIdAndProductJpaEntity_Id(Long id, Long productId);

    @Query("""
            select p from PricePolicyJpaEntity p
            where p.id = (
                select popp.id.pricePolicyId
                from ProductOptionPricePolicyJpaEntity popp
                where popp.id.productOptionId in :optionIds
                group by popp.id.pricePolicyId
                having count(distinct popp.id.productOptionId) = :#{#optionIds.size()}
            )
            """)
    Optional<PricePolicyJpaEntity> findByOptionIds(List<Long> optionIds);
}
