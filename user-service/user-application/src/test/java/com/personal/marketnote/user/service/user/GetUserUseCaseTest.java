package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.exception.UserNotFoundException;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserOauth2Vendor;
import com.personal.marketnote.user.domain.user.UserSnapshotState;
import com.personal.marketnote.user.port.in.result.AccountResult;
import com.personal.marketnote.user.port.in.result.GetUserInfoResult;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.personal.marketnote.user.exception.ExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseTest {
    @Mock
    private FindUserPort findUserPort;

    @InjectMocks
    private GetUserService getUserService;

    @Test
    @DisplayName("회원 ID를 전송해 회원 정보 결과를 조회한다")
    void getUserInfo_success_getResult() {
        // given
        Long id = 1L;
        String nickname = "tester";
        String email = "user@test.com";
        String fullName = "홍길동";
        String phoneNumber = "010-1111-2222";
        String referenceCode = "ref-123";
        Role role = Role.getBuyer();
        List<UserOauth2Vendor> userOauth2Vendors = List.of(
                UserOauth2Vendor.of(AuthVendor.KAKAO, "kakao-oidc"),
                UserOauth2Vendor.of(AuthVendor.GOOGLE, "google-oidc")
        );
        LocalDateTime signedUpAt = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime lastLoggedInAt = LocalDateTime.of(2024, 1, 2, 11, 0);
        EntityStatus status = EntityStatus.ACTIVE;
        boolean withdrawalYn = false;
        Long orderNum = 7L;

        User user = buildUser(
                id,
                nickname,
                email,
                fullName,
                phoneNumber,
                referenceCode,
                role,
                userOauth2Vendors,
                signedUpAt,
                lastLoggedInAt,
                status,
                withdrawalYn,
                orderNum
        );

        when(findUserPort.findById(id)).thenReturn(Optional.of(user));

        // when
        GetUserInfoResult result = getUserService.getUserInfo(id);

        // then
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.nickname()).isEqualTo(nickname);
        assertThat(result.email()).isEqualTo(email);
        assertThat(result.fullName()).isEqualTo(fullName);
        assertThat(result.phoneNumber()).isEqualTo(phoneNumber);
        assertThat(result.referenceCode()).isEqualTo(referenceCode);
        assertThat(result.roleId()).isEqualTo(role.getId());
        assertThat(result.signedUpAt()).isEqualTo(signedUpAt);
        assertThat(result.lastLoggedInAt()).isEqualTo(lastLoggedInAt);
        assertThat(result.status()).isEqualTo(status.name());
        assertThat(result.isWithdrawn()).isFalse();
        assertThat(result.orderNum()).isEqualTo(orderNum);
        assertThat(result.accountInfo().accounts()).containsExactly(
                new AccountResult(AuthVendor.KAKAO, "kakao-oidc"),
                new AccountResult(AuthVendor.GOOGLE, "google-oidc")
        );

        verify(findUserPort).findById(id);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 조회하면 예외를 던진다")
    void getUserInfo_notFound_throws() {
        // given
        Long id = 999L;
        when(findUserPort.findById(id)).thenReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> getUserService.getUserInfo(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, id));

        verify(findUserPort).findById(id);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("회원 ID를 전송해 회원 도메인을 조회한다")
    void getUser_success_returnsUser() {
        // given
        Long id = 10L;
        User user = buildDefaultUser(id, EntityStatus.ACTIVE, false, List.of());

        when(findUserPort.findById(id)).thenReturn(Optional.of(user));

        // when
        User result = getUserService.getUser(id);

        // then
        assertThat(result).isSameAs(user);
        verify(findUserPort).findById(id);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("회원 ID로 조회 시 존재하지 않으면 예외를 던진다")
    void getUser_notFound_throws() {
        // given
        Long id = 404L;
        when(findUserPort.findById(id)).thenReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> getUserService.getUser(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, id));

        verify(findUserPort).findById(id);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("인증 제공자와 OIDC ID를 전송해 회원 도메인을 조회한다")
    void getUser_byAuthVendorAndOidcId_success_returnsUser() {
        // given
        AuthVendor authVendor = AuthVendor.KAKAO;
        String oidcId = "oidc-123";
        User user = buildDefaultUser(11L, EntityStatus.ACTIVE, false, List.of());

        when(findUserPort.findByAuthVendorAndOidcId(authVendor, oidcId)).thenReturn(Optional.of(user));

        // when
        User result = getUserService.getUser(authVendor, oidcId);

        // then
        assertThat(result).isSameAs(user);
        verify(findUserPort).findByAuthVendorAndOidcId(authVendor, oidcId);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("인증 제공자와 OIDC ID로 조회 시 존재하지 않으면 예외를 던진다")
    void getUser_byAuthVendorAndOidcId_notFound_throws() {
        // given
        AuthVendor authVendor = AuthVendor.GOOGLE;
        String oidcId = "missing-oidc";
        when(findUserPort.findByAuthVendorAndOidcId(authVendor, oidcId)).thenReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> getUserService.getUser(authVendor, oidcId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format(USER_OIDC_ID_NOT_FOUND_EXCEPTION_MESSAGE, oidcId));

        verify(findUserPort).findByAuthVendorAndOidcId(authVendor, oidcId);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("추천 코드를 전송해 회원 도메인을 조회한다")
    void getUser_byReferenceCode_success_returnsUser() {
        // given
        String referenceCode = "ref-123";
        User user = buildDefaultUser(12L, EntityStatus.ACTIVE, false, List.of());

        when(findUserPort.findByReferenceCode(referenceCode)).thenReturn(Optional.of(user));

        // when
        User result = getUserService.getUser(referenceCode);

        // then
        assertThat(result).isSameAs(user);
        verify(findUserPort).findByReferenceCode(referenceCode);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("추천 코드로 조회 시 존재하지 않으면 예외를 던진다")
    void getUser_byReferenceCode_notFound_throws() {
        // given
        String referenceCode = "missing-ref";
        when(findUserPort.findByReferenceCode(referenceCode)).thenReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> getUserService.getUser(referenceCode))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format(USER_REFERENCE_CODE_NOT_FOUND_EXCEPTION_MESSAGE, referenceCode));

        verify(findUserPort).findByReferenceCode(referenceCode);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("비활성 회원이면 상태와 탈퇴 여부를 반환한다")
    void getUserInfo_inactiveUser_mapsStatusAndWithdrawn() {
        // given
        Long id = 2L;
        User user = buildDefaultUser(id, EntityStatus.INACTIVE, true, List.of());

        when(findUserPort.findById(id)).thenReturn(Optional.of(user));

        // when
        GetUserInfoResult result = getUserService.getUserInfo(id);

        // then
        assertThat(result.status()).isEqualTo(EntityStatus.INACTIVE.name());
        assertThat(result.isWithdrawn()).isTrue();
        assertThat(result.accountInfo().accounts()).isEmpty();

        verify(findUserPort).findById(id);
        verifyNoMoreInteractions(findUserPort);
    }

    @Test
    @DisplayName("비노출 회원이면 상태와 계정 목록을 반환한다")
    void getUserInfo_unexposedUser_mapsStatusAndAccounts() {
        // given
        Long id = 3L;
        List<UserOauth2Vendor> userOauth2Vendors = List.of(
                UserOauth2Vendor.of(AuthVendor.APPLE, "apple-oidc")
        );
        User user = buildDefaultUser(id, EntityStatus.UNEXPOSED, false, userOauth2Vendors);

        when(findUserPort.findById(id)).thenReturn(Optional.of(user));

        // when
        GetUserInfoResult result = getUserService.getUserInfo(id);

        // then
        assertThat(result.status()).isEqualTo(EntityStatus.UNEXPOSED.name());
        assertThat(result.isWithdrawn()).isFalse();
        assertThat(result.accountInfo().accounts()).containsExactly(
                new AccountResult(AuthVendor.APPLE, "apple-oidc")
        );

        verify(findUserPort).findById(id);
        verifyNoMoreInteractions(findUserPort);
    }

    private User buildDefaultUser(
            Long id,
            EntityStatus status,
            boolean withdrawalYn,
            List<UserOauth2Vendor> userOauth2Vendors
    ) {
        return buildUser(
                id,
                "tester",
                "user@test.com",
                "홍길동",
                "010-1111-2222",
                "ref-123",
                Role.getBuyer(),
                userOauth2Vendors,
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 11, 0),
                status,
                withdrawalYn,
                5L
        );
    }

    private User buildUser(
            Long id,
            String nickname,
            String email,
            String fullName,
            String phoneNumber,
            String referenceCode,
            Role role,
            List<UserOauth2Vendor> userOauth2Vendors,
            LocalDateTime signedUpAt,
            LocalDateTime lastLoggedInAt,
            EntityStatus status,
            boolean withdrawalYn,
            Long orderNum
    ) {
        UserSnapshotState state = UserSnapshotState.builder()
                .id(id)
                .userKey(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .nickname(nickname)
                .email(email)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .role(role)
                .userOauth2Vendors(userOauth2Vendors)
                .userTerms(List.of())
                .signedUpAt(signedUpAt)
                .lastLoggedInAt(lastLoggedInAt)
                .status(status)
                .withdrawalYn(withdrawalYn)
                .orderNum(orderNum)
                .build();

        return User.from(state);
    }
}
