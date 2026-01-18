package com.personal.marketnote.reward.service.offerwall;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.exception.token.VendorVerificationFailedException;
import com.personal.marketnote.common.exception.UserNotFoundException;
import com.personal.marketnote.common.security.vendor.VendorVerificationProcessor;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.configuration.AdpopcornHashKeyProperties;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.domain.point.UserPointChangeType;
import com.personal.marketnote.reward.domain.point.UserPointSourceType;
import com.personal.marketnote.reward.exception.DuplicateOfferwallRewardException;
import com.personal.marketnote.reward.exception.RewardTargetInfoNotFoundException;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;
import com.personal.marketnote.reward.port.in.command.point.ModifyUserPointCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.GetPostOfferwallMapperUseCase;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.in.usecase.point.GetUserPointUseCase;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import com.personal.marketnote.reward.service.point.ModifyUserPointService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterOfferwallRewardRewardService implements RegisterOfferwallRewardUseCase {
    private final GetUserPointUseCase getUserPointUseCase;
    private final ModifyUserPointService modifyUserPointService;
    private final SaveOfferwallMapperPort saveOfferwallMapperPort;
    private final GetPostOfferwallMapperUseCase getPostOfferwallMapperUseCase;
    private final AdpopcornHashKeyProperties adpopcornHashKeyProperties;

    @Override
    public OfferwallMapper register(RegisterOfferwallRewardCommand command) {
//        validateSignature(command);
        validateUser(command);
        validateDuplicate(command);

        OfferwallMapper offerwallMapper = saveOfferwallMapperPort.save(
                OfferwallMapper.from(RewardCommandToStateMapper.mapToOfferwallMapperCreateState(command, true))
        );

        // 회원 리워드 포인트 적립
        modifyUserPointService.modify(
                ModifyUserPointCommand.builder()
                        .userId(FormatConverter.parseId(command.userId()))
                        .changeType(UserPointChangeType.ACCRUAL)
                        .amount(command.quantity())
                        .sourceType(UserPointSourceType.OFFERWALL)
                        .sourceId(offerwallMapper.getId())
                        .reason(String.format("%s 리워드 보상 적립", command.offerwallType().getDescription()))
                        .build()
        );

        return offerwallMapper;
    }

    private void validateSignature(RegisterOfferwallRewardCommand command) {
        String hashKey = resolveHashKey(command);
        String plainText = buildPlainText(command);
        VendorVerificationProcessor.validateAdpopcornSignature(hashKey, plainText, command.signedValue());
    }

    private String resolveHashKey(RegisterOfferwallRewardCommand command) {
        if (command.isAndroid()) {
            return requireHashKey(adpopcornHashKeyProperties.getAndroid());
        }

        if (command.isIos()) {
            return requireHashKey(adpopcornHashKeyProperties.getIos());
        }

        throw new RewardTargetInfoNotFoundException("애드팝콘 리워드 지급 대상 디바이스 정보가 없습니다.");
    }

    private String requireHashKey(String hashKey) {
        if (FormatValidator.hasValue(hashKey)) {
            return hashKey;
        }

        throw new VendorVerificationFailedException("애드팝콘 해시 키가 설정되지 않았습니다.");
    }

    private String buildPlainText(RegisterOfferwallRewardCommand command) {
        return command.userId()
                + command.rewardKey()
                + command.quantity()
                + command.campaignKey();
    }

    private void validateUser(RegisterOfferwallRewardCommand command) {
        Long userId = FormatConverter.parseId(command.userId());
        if (!getUserPointUseCase.existsUserPoint(userId)) {
            throw new UserNotFoundException(String.format("회원 정보를 찾을 수 없습니다. userId: %s", userId));
        }
    }

    private void validateDuplicate(RegisterOfferwallRewardCommand command) {
        if (getPostOfferwallMapperUseCase.existsSucceededOfferwallMapper(
                command.offerwallType(),
                command.rewardKey()
        )) {
            throw new DuplicateOfferwallRewardException(command.rewardKey());
        }
    }
}
