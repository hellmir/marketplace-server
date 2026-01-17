package com.personal.marketnote.reward.port.in.usecase.point;

public interface GetUserPointUseCase {
    /**
     * @param userId 회원 ID
     * @return 회원 포인트 존재 여부 {@link boolean}
     * @Date 2026-01-17
     * @Author 성효빈
     * @Description 회원 포인트가 존재하는지 여부를 확인합니다.
     */
    boolean existsUserPoint(Long userId);
}
