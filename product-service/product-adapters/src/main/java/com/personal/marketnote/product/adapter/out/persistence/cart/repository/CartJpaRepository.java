package com.personal.marketnote.product.adapter.out.persistence.cart.repository;

import com.personal.marketnote.product.adapter.out.persistence.cart.entity.CartProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartProductJpaEntity, Long> {
    List<CartProductJpaEntity> findByIdUserId(Long userId);

    @Query(
            """
                    SELECT c FROM CartProductJpaEntity c 
                    WHERE 1 = 1
                    AND c.id.pricePolicyId = :pricePolicyId
                    """
    )
    Optional<CartProductJpaEntity> findByPricePolicyId(Long pricePolicyId);

    @Query(
            """
                    SELECT c FROM CartProductJpaEntity c 
                    WHERE 1 = 1
                    AND c.id.userId = :userId 
                    AND c.id.pricePolicyId = :pricePolicyId
                    """
    )
    Optional<CartProductJpaEntity> findByIdUserIdAndPricePolicyId(Long userId, Long pricePolicyId);
}
