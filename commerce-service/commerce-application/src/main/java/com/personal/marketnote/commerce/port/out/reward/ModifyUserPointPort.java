package com.personal.marketnote.commerce.port.out.reward;

import java.util.List;

public interface ModifyUserPointPort {
    void accrueSharedPurchasePoints(List<Long> sharerIds);
}
