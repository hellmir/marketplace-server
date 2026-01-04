package com.personal.marketnote.product.port.out.cart;

import java.util.List;

public interface DeleteCartProductPort {
    void delete(Long userId, List<Long> pricePolicyIds);
}
