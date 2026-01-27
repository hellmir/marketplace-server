package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.shop.FasstoShopMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoShopResult;

public interface UpdateFasstoShopPort {
    UpdateFasstoShopResult updateShop(FasstoShopMapper request);
}
