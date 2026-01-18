package com.personal.marketnote.reward.adapter.in.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.common.domain.exception.token.VendorVerificationFailedException;
import com.personal.marketnote.common.exception.UserNotFoundException;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.reward.adapter.in.offerwall.apidocs.AdpopcornCallbackApiDocs;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.offerwall.UserDeviceType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationTargetType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorName;
import com.personal.marketnote.reward.exception.DuplicateOfferwallRewardException;
import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.HandleOfferwallRewardUseCase;
import com.personal.marketnote.reward.utility.VendorCommunicationFailureHandler;
import com.personal.marketnote.reward.utility.VendorCommunicationPayloadGenerator;
import com.personal.marketnote.reward.utility.VendorCommunicationRecorder;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/offerwalls")
@Tag(name = "오퍼월 API", description = "오퍼월 관련 API")
@RequiredArgsConstructor
@Slf4j
public class OfferwallController {
    private final HandleOfferwallRewardUseCase handleOfferwallRewardUseCase;
    private final VendorCommunicationRecorder vendorCommunicationRecorder;
    private final VendorCommunicationFailureHandler vendorCommunicationFailureHandler;
    private final VendorCommunicationPayloadGenerator vendorCommunicationPayloadGenerator;

    /**
     * 아드팝콘 리워드 지급 콜백 엔드포인트
     *
     * @param rewardKey    리워드 키
     * @param userId       사용자 키
     * @param campaignKey  캠페인 키
     * @param campaignType 캠페인 타입
     * @param campaignName 캠페인 이름
     * @param quantity     수량
     * @param signedValue  서명 값(검증 필요)
     * @param appKey       앱 키
     * @param appName      앱 이름
     * @param adid         구글 광고 ID
     * @param idfa         IDFA
     * @param timeStamp    캠페인 완료일시
     * @Author 성효빈
     * @Date 2026-01-16
     */
    @PostMapping(value = "/adpopcorn/callback")
    @AdpopcornCallbackApiDocs
    public ResponseEntity<String> handleAdpopcornCallback(
            @RequestParam("reward_key") String rewardKey,
            @RequestParam("usn") String userId,
            @RequestParam("campaign_key") String campaignKey,
            @RequestParam("user_device_type") UserDeviceType userDeviceType,
            @RequestParam(value = "campaign_type", required = false) Integer campaignType,
            @RequestParam(value = "campaign_name", required = false) String campaignName,
            @RequestParam(value = "quantity") Long quantity,
            @RequestParam(value = "signed_value") String signedValue,
            @RequestParam(value = "app_key", required = false) Integer appKey,
            @RequestParam(value = "app_name", required = false) String appName,
            @RequestParam(value = "adid", required = false) String adid,
            @RequestParam(value = "idfa", required = false) String idfa,
            @RequestParam(value = "time_stamp", required = false) String timeStamp
    ) {
        LocalDateTime attendedAt = FormatConverter.parseToLocalDateTime(timeStamp);

        JsonNode payloadJson = vendorCommunicationPayloadGenerator.buildPayloadJson(
                rewardKey,
                userId,
                campaignKey,
                campaignType,
                campaignName,
                quantity,
                signedValue,
                appKey,
                appName,
                adid,
                idfa,
                timeStamp
        );
        String payloadString = payloadJson.toString();

        RegisterOfferwallRewardCommand command = RegisterOfferwallRewardCommand.builder()
                .offerwallType(OfferwallType.ADPOPCORN)
                .rewardKey(rewardKey)
                .userId(userId)
                .userDeviceType(userDeviceType)
                .campaignKey(campaignKey)
                .campaignType(campaignType)
                .campaignName(campaignName)
                .quantity(quantity)
                .signedValue(signedValue)
                .appKey(appKey)
                .appName(appName)
                .adid(adid)
                .idfa(idfa)
                .attendedAt(attendedAt)
                .build();

        RewardVendorCommunicationTargetType targetType = RewardVendorCommunicationTargetType.OFFERWALL;
        RewardVendorName vendorName = RewardVendorName.ADPOPCORN;

        try {
            Long id = handleOfferwallRewardUseCase.handle(command);
            JsonNode successPayloadJson = vendorCommunicationPayloadGenerator.buildResponsePayloadJson(true, 1, "success");
            String successPayload = successPayloadJson.toString();

            vendorCommunicationRecorder.record(
                    targetType, RewardVendorCommunicationType.REQUEST, id, vendorName, payloadString, payloadJson
            );
            vendorCommunicationRecorder.record(
                    targetType, RewardVendorCommunicationType.RESPONSE, id, vendorName, successPayload, successPayloadJson
            );

            return ResponseEntity.ok(successPayload);
        } catch (VendorVerificationFailedException e) {
            // 서명 검증 실패
            return vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 1100, "invalid signed value");
        } catch (UserNotFoundException e) {
            // 회원 포인트 도메인 정보 조회 실패
            return vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 3200, "invalid user");
        } catch (DuplicateOfferwallRewardException e) {
            // 중복 리워드 지급 시도
            return vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 3100, "duplicate transaction");
        } catch (Exception e) {
            // 그 외
            return vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 4000, "custom error message");
        }
    }
}
