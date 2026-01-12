package com.personal.marketnote.community.domain.post;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FaqPostCategory implements PostCategory {
    TOP_NOTICE("TOP 공지"),
    MEMBER("회원"),
    ORDER_PAYMENT_BULK("주문/결제/대량 주문"),
    CANCEL_EXCHANGE_REFUND("취소/교환/환불"),
    DELIVERY("배송"),
    EVENT_COUPON_POINT("이벤트/쿠폰/적립금"),
    PRODUCT("상품");

    private final String description;

    @Override
    public Boolean isMe(String categoryCode) {
        return FormatValidator.equals(name(), categoryCode);
    }
}
