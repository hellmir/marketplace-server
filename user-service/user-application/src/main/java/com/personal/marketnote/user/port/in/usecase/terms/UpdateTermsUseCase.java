package com.personal.marketnote.user.port.in.usecase.terms;

import com.personal.marketnote.user.port.in.command.AcceptOrCancelTermsCommand;
import com.personal.marketnote.user.port.in.result.GetUserTermsResult;

public interface UpdateTermsUseCase {
    /**
     * @param userId                     회원 ID
     * @param acceptOrCancelTermsCommand 동의/철회할 이용 약관 ID 목록
     * @return 회원 이용 약관 동의 여부 목록 {@link GetUserTermsResult}
     * @Author 성효빈
     * @Date 2025-12-27
     * @Description 회원이 이용 약관에 동의하거나 철회합니다.
     */
    GetUserTermsResult acceptOrCancelTerms(Long userId, AcceptOrCancelTermsCommand acceptOrCancelTermsCommand);
}
