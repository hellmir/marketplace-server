package com.personal.marketnote.commerce.domain.order;

public enum OrderStatus {
    PAYMENT_PENDING("결제 대기"),
    PAID("결제 완료"),
    FAILED("결제 실패"),
    PREPARING("상품 준비중"),
    PREPARED("상품 준비 완료"),
    CANCEL_REQUESTED("주문 취소 요청됨"),
    CANCELLED("주문 취소"),
    SHIPPING("배송중"),
    DELIVERED("배송 완료"),
    CONFIRMED("구매 확정"),
    EXCHANGE_REQUESTED("교환 요청됨"),
    EXCHANGE_RECALLING("교환 회수 중"),
    EXCHANGE_SHIPPING("교환 배송 중"),
    EXCHANGED("교환 완료"),
    REFUND_REQUESTED("환불 요청됨"),
    REFUND_RECALLING("환불 회수 중"),
    REFUND_SHIPPING("환불 배송 중"),
    PARTIALLY_REFUNDED("부분 환불됨"),
    REFUNDED("환불 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRefunded() {
        return this == REFUNDED;
    }

    public static OrderStatus getPartiallyRefunded() {
        return PARTIALLY_REFUNDED;
    }

    public boolean isPaid() {
        return this == PAID;
    }

    public boolean isMe(OrderStatus orderStatus) {
        return this == orderStatus;
    }

    public boolean isPending() {
        return this == PAYMENT_PENDING;
    }
}
