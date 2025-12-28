package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.in.result.GetUserResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface GetUserUseCase {
    /**
     * @param id 회원 ID
     * @return 회원 도메인 {@link User}
     * @Date 2025-12-28
     * @Author 성효빈
     * @Description 회원을 조회합니다.
     */
    User getUser(Long id);

    /**
     * @param authVendor 인증 제공자
     * @param oidcId     외부 인증 ID
     * @return 회원 도메인 {@link User}
     * @Date 2025-12-28
     * @Author 성효빈
     * @Description 회원을 조회합니다.
     */
    User getUser(AuthVendor authVendor, String oidcId);

    /**
     * @param email 회원 이메일 주소
     * @return 회원 도메인 {@link User}
     * @Date 2025-12-28
     * @Author 성효빈
     * @Description 활성화/비활성화/비노출 회원을 조회합니다.
     */
    User getAllStatusUser(String email);

    /**
     * @param id 회원 ID
     * @return 회원 정보 조회 결과 {@link GetUserResult}
     * @Date 2025-12-28
     * @Author 성효빈
     * @Description 회원 DTO를 조회합니다.
     */
    GetUserResult getUserInfo(Long id);
}
