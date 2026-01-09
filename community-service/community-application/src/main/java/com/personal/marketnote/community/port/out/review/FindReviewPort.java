package com.personal.marketnote.community.port.out.review;

public interface FindReviewPort {
    boolean existsByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId);
}
