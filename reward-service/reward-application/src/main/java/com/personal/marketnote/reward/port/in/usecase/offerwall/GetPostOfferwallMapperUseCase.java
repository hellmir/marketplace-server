package com.personal.marketnote.reward.port.in.usecase.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;

public interface GetPostOfferwallMapperUseCase {
    /**
     * @param offerwallType 오퍼월 타입
     * @param rewardKey     리워드 키
     * @return 성공한 오퍼월 리워드 지급 정보 존재 여부 {@link boolean}
     * @Date 2026-01-17
     * @Author 성효빈
     * @Description 성공한 오퍼월 리워드 지급 정보 존재 여부를 조회합니다.
     */
    boolean existsSucceededOfferwallMapper(OfferwallType offerwallType, String rewardKey);
}
