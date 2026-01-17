package com.personal.marketnote.reward.service.offerwall;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.exception.token.VendorVerificationFailedException;
import com.personal.marketnote.common.security.vendor.VendorVerificationProcessor;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.configuration.AdpopcornHashKeyProperties;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.exception.DuplicateOfferwallRewardException;
import com.personal.marketnote.reward.exception.RewardTargetInfoNotFoundException;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.GetPostOfferwallMapperUseCase;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterAdpopcornRewardService implements RegisterOfferwallRewardUseCase {
    private final SaveOfferwallMapperPort saveOfferwallMapperPort;
    private final GetPostOfferwallMapperUseCase getPostOfferwallMapperUseCase;
    private final AdpopcornHashKeyProperties adpopcornHashKeyProperties;

    @Override
    public OfferwallMapper register(RegisterOfferwallRewardCommand command) {
        validateSignature(command);
        validateDuplicate(command);

        return saveOfferwallMapperPort.save(
                OfferwallMapper.from(RewardCommandToStateMapper.mapToOfferwallMapperCreateState(command))
        );
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

    private void validateDuplicate(RegisterOfferwallRewardCommand command) {
        if (getPostOfferwallMapperUseCase.existsOfferwallMapper(
                command.offerwallType(),
                command.rewardKey()
        )) {
            throw new DuplicateOfferwallRewardException(command.rewardKey());
        }
    }
}
