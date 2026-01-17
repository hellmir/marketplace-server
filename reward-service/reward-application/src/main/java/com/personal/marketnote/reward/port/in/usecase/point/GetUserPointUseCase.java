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

    /**
     * @param userId 회원 ID
     * @return 회원 포인트 정보
     * @Date 2026-01-17
     * @Author ALLBUS
     * @Description 회원 포인트 정보를 조회합니다. 존재하지 않으면 {@link com.personal.marketnote.common.exception.UserNotFoundException} 발생
     */
    com.personal.marketnote.reward.domain.point.UserPoint getUserPoint(Long userId);
}
