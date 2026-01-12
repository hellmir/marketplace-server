package com.personal.marketnote.commerce.adapter.in.client.order.controller;

import com.personal.marketnote.commerce.adapter.in.client.order.controller.apidocs.*;
import com.personal.marketnote.commerce.adapter.in.client.order.mapper.OrderRequestToCommandMapper;
import com.personal.marketnote.commerce.adapter.in.client.order.request.ChangeOrderStatusRequest;
import com.personal.marketnote.commerce.adapter.in.client.order.request.RegisterOrderRequest;
import com.personal.marketnote.commerce.adapter.in.client.order.response.GetOrderResponse;
import com.personal.marketnote.commerce.adapter.in.client.order.response.GetOrdersResponse;
import com.personal.marketnote.commerce.adapter.in.client.order.response.RegisterOrderResponse;
import com.personal.marketnote.commerce.domain.order.OrderPeriod;
import com.personal.marketnote.commerce.domain.order.OrderStatusFilter;
import com.personal.marketnote.commerce.port.in.command.order.GetBuyerOrderHistoryCommand;
import com.personal.marketnote.commerce.port.in.command.order.UpdateOrderProductReviewStatusCommand;
import com.personal.marketnote.commerce.port.in.result.order.GetOrderHistoryResult;
import com.personal.marketnote.commerce.port.in.result.order.GetOrderResult;
import com.personal.marketnote.commerce.port.in.result.order.GetOrdersResult;
import com.personal.marketnote.commerce.port.in.result.order.RegisterOrderResult;
import com.personal.marketnote.commerce.port.in.usecase.order.ChangeOrderStatusUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.RegisterOrderUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.UpdateOrderProductUseCase;
import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "주문 API", description = "주문 관련 API")
@RequiredArgsConstructor
public class OrderController {
    private final RegisterOrderUseCase registerOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ChangeOrderStatusUseCase changeOrderStatusUseCase;
    private final UpdateOrderProductUseCase updateOrderProductUseCase;

    /**
     * 주문 등록
     *
     * @param request   주문 등록 요청
     * @param principal 인증된 사용자 정보
     * @return 주문 등록 응답 {@link RegisterOrderResponse}
     * @Author 성효빈
     * @Date 2026-01-05
     * @Description 주문을 등록합니다.
     */
    @PostMapping
    @RegisterOrderApiDocs
    public ResponseEntity<BaseResponse<RegisterOrderResponse>> registerOrder(
            @Valid @RequestBody RegisterOrderRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        Long buyerId = ElementExtractor.extractUserId(principal);

        RegisterOrderResult result = registerOrderUseCase.registerOrder(
                OrderRequestToCommandMapper.mapToCommand(request, buyerId)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterOrderResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "주문 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 주문 정보 조회
     *
     * @param id 주문 ID
     * @return 주문 정보 조회 응답 {@link GetOrderResponse}
     * @Author 성효빈
     * @Date 2026-01-05
     * @Description 주문 정보를 조회합니다.
     */
    @GetMapping("/{id}")
    @GetOrderInfoApiDocs
    public ResponseEntity<BaseResponse<GetOrderResponse>> getOrder(@PathVariable("id") Long id) {
        GetOrderResult getOrderResult = getOrderUseCase.getOrderAndOrderProducts(id);

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetOrderResponse.from(getOrderResult),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "주문 정보 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 주문 내역 조회
     *
     * @param principal 인증된 사용자 정보
     * @return 주문 내역 조회 응답 {@link GetOrdersResponse}
     * @Author 성효빈
     * @Date 2026-01-05
     * @Description 회원 주문 내역을 조회합니다.
     */
    @GetMapping
    @GetOrdersApiDocs
    public ResponseEntity<BaseResponse<GetOrdersResponse>> getBuyerOrderHistory(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
            @RequestParam(value = "period", required = false) OrderPeriod period,
            @RequestParam(value = "status", required = false) OrderStatusFilter statusFilter,
            @RequestParam(value = "productName", required = false) String productName
    ) {
        GetOrdersResult getOrderResult = getOrderUseCase.getBuyerOrderHistory(
                GetBuyerOrderHistoryCommand.of(
                        ElementExtractor.extractUserId(principal),
                        period,
                        statusFilter,
                        productName
                )
        );
        GetOrderHistoryResult getOrderHistoryResult = GetOrderHistoryResult.from(
                getOrderResult.orders(),
                getOrderResult.orderedProducts()
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetOrdersResponse.from(getOrderHistoryResult),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 주문 내역 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 주문 상태 변경
     *
     * @param request 주문 상태 변겅 요청
     * @Author 성효빈
     * @Date 2026-01-05
     * @Description 주문 상태를 변경합니다.
     */
    @PatchMapping("/{id}")
    @ChangeOrderStatusApiDocs
    public ResponseEntity<BaseResponse<Void>> changeOrderStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody ChangeOrderStatusRequest request
    ) {
        changeOrderStatusUseCase.changeOrderStatus(
                OrderRequestToCommandMapper.mapToCommand(id, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "주문 상태 변경 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 주문 상품의 리뷰 작성 여부 업데이트
     *
     * @param orderId       주문 ID
     * @param pricePolicyId 가격 정책 ID
     * @param isReviewed    리뷰 작성 여부
     * @Author 성효빈
     * @Date 2026-01-12
     * @Description 주문 상품의 리뷰 작성 여부를 업데이트합니다.
     */
    @PatchMapping("/{orderId}/order-products/{pricePolicyId}/review")
    @PreAuthorize(ADMIN_POINTCUT)
    @UpdateOrderProductReviewStatusApiDocs
    public ResponseEntity<BaseResponse<Void>> updateOrderProductReviewStatus(
            @PathVariable("orderId") Long orderId,
            @PathVariable("pricePolicyId") Long pricePolicyId,
            @Valid @RequestParam Boolean isReviewed
    ) {
        updateOrderProductUseCase.updateReviewStatus(
                UpdateOrderProductReviewStatusCommand.of(orderId, pricePolicyId, isReviewed)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "리뷰 작성 여부 업데이트 성공"
                ),
                HttpStatus.OK
        );
    }
}
