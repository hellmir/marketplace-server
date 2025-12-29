package com.personal.marketnote.user.port.in.usecase.user;

public interface WithdrawUseCase {
    /**
     * @param id 회원 ID
     * @Date 2025-12-29
     * @Author 성효빈
     * @Description 회원을 탈퇴합니다.
     */
    void withdrawUser(Long id);
}
