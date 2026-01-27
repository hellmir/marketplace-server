package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.shop.FasstoShopQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoShopsResult;

public interface GetFasstoShopsPort {
    GetFasstoShopsResult getShops(FasstoShopQuery query);
}
