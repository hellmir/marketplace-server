package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindPricePoliciesPort {
    List<PricePolicy> findByProductId(Long productId);

    List<PricePolicy> findByIds(List<Long> ids);

    List<PricePolicy> findActivePage(
            List<Long> pricePolicyIds,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );

    List<PricePolicy> findActivePageByCategoryId(
            Long categoryId,
            List<Long> pricePolicyIds,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    );
}
