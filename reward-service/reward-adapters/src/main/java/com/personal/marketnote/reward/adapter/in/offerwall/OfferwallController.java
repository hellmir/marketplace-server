package com.personal.marketnote.reward.adapter.in.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.domain.exception.token.VendorVerificationFailedException;
import com.personal.marketnote.common.exception.UserNotFoundException;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.reward.adapter.in.offerwall.apidocs.AdpopcornCallbackApiDocs;
import com.personal.marketnote.reward.adapter.in.offerwall.apidocs.TnkCallbackApiDocs;
import com.personal.marketnote.reward.adapter.in.point.response.UpdateUserPointResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;

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
     * @param userKey      회원 키
     * @param campaignKey  캠페인 키
     * @param campaignType 캠페인 타입
     * @param campaignName 캠페인 이름
     * @param quantity     수량
     * @param signedValue  서명 값(검증 필요)
     * @param appKey       앱 키
     * @param appName      앱 이름
     * @param adid         구글 광고 ID
     * @param idfa         IDFA
     * @param attendedAt   캠페인 완료일시
     * @Author 성효빈
     * @Date 2026-01-16
     */
    @PostMapping(value = "/adpopcorn/callback")
    @AdpopcornCallbackApiDocs
    public ResponseEntity<String> handleAdpopcornCallback(
            @RequestParam("reward_key") String rewardKey,
            @RequestParam("usn") String userKey,
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
            @RequestParam(value = "time_stamp", required = false) String attendedAt
    ) {
        LocalDateTime parsedAttendedAt = FormatConverter.parseToLocalDateTime(attendedAt);

        JsonNode payloadJson = vendorCommunicationPayloadGenerator.buildAdpopcornPayloadJson(
                rewardKey,
                userKey,
                userDeviceType,
                campaignKey,
                campaignType,
                campaignName,
                quantity,
                signedValue,
                appKey,
                appName,
                adid,
                idfa,
                attendedAt
        );
        String payloadString = payloadJson.toString();

        RegisterOfferwallRewardCommand command = RegisterOfferwallRewardCommand.builder()
                .offerwallType(OfferwallType.ADPOPCORN)
                .rewardKey(rewardKey)
                .userKey(userKey)
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
                .attendedAt(parsedAttendedAt)
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

    /**
     * TNK 리워드 지급 콜백 엔드포인트
     *
     * @param rewardKey    리워드 키
     * @param userKey      회원 키
     * @param campaignKey  캠페인 키
     * @param campaignType 캠페인 타입
     * @param campaignName 캠페인 이름
     * @param quantity     수량
     * @param signedValue  서명 값(검증 필요)
     * @param appKey       앱 키
     * @param appName      앱 이름
     * @param adid         구글 광고 ID
     * @param idfa         IDFA
     * @param attendedAt   캠페인 완료일시
     * @Author 성효빈
     * @Date 2026-01-19
     */
    @PostMapping(value = "/tnk/callback")
    @TnkCallbackApiDocs
    public ResponseEntity<BaseResponse<UpdateUserPointResponse>> handleTnkCallback(
            @RequestParam("seq_id") String rewardKey,
            @RequestParam("md_user_nm") String userKey,
            @RequestParam("user_device_type") UserDeviceType userDeviceType,
            @RequestParam(value = "campaign_key", required = false) String campaignKey,
            @RequestParam(value = "actn_id", required = false) Integer campaignType,
            @RequestParam(value = "campaign_name", required = false) String campaignName,
            @RequestParam(value = "pay_pnt") Long quantity,
            @RequestParam(value = "md_chk") String signedValue,
            @RequestParam(value = "app_key", required = false) Integer appKey,
            @RequestParam(value = "app_nm", required = false) String appName,
            @RequestParam(value = "app_id", required = false) String adid,
            @RequestParam(value = "idfa", required = false) String idfa,
            @RequestParam(value = "pay_dt", required = false) String attendedAt,
            @RequestParam(value = "pay_amt", required = false) Long revenue
    ) {
        LocalDateTime parsedAttendedAt = FormatConverter.parseToLocalDateTime(attendedAt);

        JsonNode payloadJson = vendorCommunicationPayloadGenerator.buildTnkPayloadJson(
                rewardKey,
                userKey,
                userDeviceType,
                campaignType,
                quantity,
                signedValue,
                appKey,
                appName,
                adid,
                attendedAt,
                revenue
        );
        String payloadString = payloadJson.toString();

        RegisterOfferwallRewardCommand command = RegisterOfferwallRewardCommand.builder()
                .offerwallType(OfferwallType.TNK)
                .rewardKey(rewardKey)
                .userKey(userKey)
                .userDeviceType(userDeviceType)
                .campaignKey(campaignKey)
                .campaignType(campaignType)
                .campaignName(campaignName)
                .quantity(quantity)
                .signedValue(signedValue)
                .appKey(appKey)
                .appName(appName)
                .adid(adid)
                .idfa(adid)
                .attendedAt(parsedAttendedAt)
                .build();

        RewardVendorCommunicationTargetType targetType = RewardVendorCommunicationTargetType.OFFERWALL;
        RewardVendorName vendorName = RewardVendorName.TNK;

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

            return ResponseEntity.ok(
                    BaseResponse.of(
                            HttpStatus.OK,
                            DEFAULT_SUCCESS_CODE,
                            "리워드 포인트 지급 성공"
                    )
            );
        } catch (VendorVerificationFailedException e) {
            // 서명 검증 실패
            vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 1100, "invalid signed value");
            throw e;
        } catch (UserNotFoundException e) {
            // 회원 포인트 도메인 정보 조회 실패
            vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 3200, "invalid user");
            throw e;
        } catch (DuplicateOfferwallRewardException e) {
            // 중복 리워드 지급 시도
            vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 3100, "duplicate transaction");
            throw e;
        } catch (Exception e) {
            // 그 외
            vendorCommunicationFailureHandler.handleFailure(targetType, vendorName, payloadString, payloadJson, e, 4000, "custom error message");
            throw e;
        }
    }
}
