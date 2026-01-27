package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.shop.FasstoShopMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoShopResult;

public interface RegisterFasstoShopPort {
    RegisterFasstoShopResult registerShop(FasstoShopMapper request);
}
