package com.personal.marketnote.product.adapter.out.persistence.cart.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.domain.cart.CartProduct;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CartProductJpaEntity extends BaseEntity {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
            @AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false)),
            @AttributeOverride(name = "pricePolicyId", column = @Column(name = "price_policy_id", nullable = false))
    })
    private CartId id;

    @Column(name = "quantity", nullable = false)
    private Short quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status;

    public static CartProductJpaEntity from(CartProduct cartProduct) {
        return CartProductJpaEntity.builder()
                .id(
                        new CartId(
                                cartProduct.getUserId(), cartProduct.getProductId(), cartProduct.getPricePolicyId()
                        )
                )
                .quantity(cartProduct.getQuantity())
                .status(cartProduct.getStatus())
                .build();
    }
}
