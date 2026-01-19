package com.personal.marketnote.user.port.out.reward;

public interface ModifyUserPointPort {
    /**
     * 회원 포인트 도메인 생성
     *
     * @param userId  회원 ID
     * @param userKey 회원 고유 키
     */
    void registerUserPoint(Long userId, String userKey);

    /**
     * 추천 포인트 적립
     *
     * @param referrerUserId 추천한 회원 ID
     * @param referredUserId 추천받은 회원 ID
     */
    void accrueReferralPoints(Long referrerUserId, Long referredUserId);
}
