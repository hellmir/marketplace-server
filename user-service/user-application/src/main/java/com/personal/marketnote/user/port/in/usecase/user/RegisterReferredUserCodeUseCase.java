package com.personal.marketnote.user.port.in.usecase.user;

public interface RegisterReferredUserCodeUseCase {
    /**
     * @param requestUserId    요청 회원 ID
     * @param referredUserCode 자신을 추천한 회원의 초대 코드
     * @Date 2025-12-28
     * @Author 성효빈
     * @Description 자신을 추천한 회원의 초대 코드를 등록합니다.
     */
    void registerReferredUserCode(Long requestUserId, String referredUserCode);
}
