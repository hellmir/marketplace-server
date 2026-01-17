package com.personal.marketnote.user.port.out.reward;

public interface ModifyUserPointPort {
    /**
     * 추천 포인트 적립
     *
     * @param referrerUserId 추천한 회원 ID
     * @param referredUserId 추천받은 회원 ID
     */
    void accrueReferralPoints(Long referrerUserId, Long referredUserId);
}
