package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.exception.UserNotFoundException;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.in.result.WithdrawResult;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.out.oauth.Oauth2AccountUnlinkPort;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import com.personal.marketnote.user.service.exception.UnlinkOauth2AccountFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawUseCaseTest {
    @Mock
    private GetUserUseCase getUserUseCase;
    @Mock
    private UpdateUserPort updateUserPort;
    @Mock
    private Oauth2AccountUnlinkPort oauth2AccountUnlinkPort;

    @InjectMocks
    private WithdrawService withdrawService;

    @Test
    @DisplayName("회원 탈퇴 요청 시 소셜 계정이 없으면 모두 연결 해제된 상태로 반환한다")
    void withdrawUser_withoutSocialAccounts_returnsAllTrue() {
        // given
        Long id = 1L;
        User user = mock(User.class);

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, null);

        // then
        assertThat(result.isKakaoDisconnected()).isTrue();
        assertThat(result.isGoogleDisconnected()).isTrue();
        assertThat(result.isAppleDisconnected()).isTrue();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(user, never()).removeKakaoOidcId();
        verify(user, never()).removeGoogleOidcId();
        verify(user, never()).removeAppleOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort);
        verifyNoInteractions(oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 카카오 계정이 있으면 연결 해제 후 결과를 반환한다")
    void withdrawUser_kakaoUnlinkSuccess_returnsKakaoTrue() throws Exception {
        // given
        Long id = 2L;
        String kakaoOidcId = "kakao-oidc";
        User user = mock(User.class);

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);
        when(user.getKakaoOidcId()).thenReturn(kakaoOidcId);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, null);

        // then
        assertThat(result.isKakaoDisconnected()).isTrue();
        assertThat(result.isGoogleDisconnected()).isTrue();
        assertThat(result.isAppleDisconnected()).isTrue();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(oauth2AccountUnlinkPort).unlinkKakaoAccount(kakaoOidcId);
        verify(user).removeKakaoOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort, oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 카카오 연결 해제에 실패해도 예외를 전파하지 않는다")
    void withdrawUser_kakaoUnlinkFails_returnsKakaoFalse() throws Exception {
        // given
        Long id = 3L;
        String kakaoOidcId = "kakao-oidc";
        User user = mock(User.class);
        UnlinkOauth2AccountFailedException exception = new UnlinkOauth2AccountFailedException("fail");

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);
        when(user.getKakaoOidcId()).thenReturn(kakaoOidcId);
        doThrow(exception).when(oauth2AccountUnlinkPort).unlinkKakaoAccount(kakaoOidcId);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, null);

        // then
        assertThat(result.isKakaoDisconnected()).isFalse();
        assertThat(result.isGoogleDisconnected()).isTrue();
        assertThat(result.isAppleDisconnected()).isTrue();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(oauth2AccountUnlinkPort).unlinkKakaoAccount(kakaoOidcId);
        verify(user).removeKakaoOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort, oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 구글 계정이 있고 토큰이 있으면 연결 해제 후 결과를 반환한다")
    void withdrawUser_googleUnlinkSuccess_returnsGoogleTrue() throws Exception {
        // given
        Long id = 4L;
        String googleAccessToken = "google-token";
        User user = mock(User.class);

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);
        when(user.hasGoogleAccount()).thenReturn(true);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, googleAccessToken);

        // then
        assertThat(result.isKakaoDisconnected()).isTrue();
        assertThat(result.isGoogleDisconnected()).isTrue();
        assertThat(result.isAppleDisconnected()).isTrue();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(oauth2AccountUnlinkPort).unlinkGoogleAccount(googleAccessToken);
        verify(user).removeGoogleOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort, oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 구글 계정이 있지만 토큰이 없으면 연결 해제 결과가 실패로 반환된다")
    void withdrawUser_googleTokenMissing_returnsGoogleFalse() throws Exception {
        // given
        Long id = 5L;
        User user = mock(User.class);

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);
        when(user.hasGoogleAccount()).thenReturn(true);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, null);

        // then
        assertThat(result.isKakaoDisconnected()).isTrue();
        assertThat(result.isGoogleDisconnected()).isFalse();
        assertThat(result.isAppleDisconnected()).isTrue();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(oauth2AccountUnlinkPort, never()).unlinkGoogleAccount(anyString());
        verify(user).removeGoogleOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort, oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 구글 연결 해제에 실패해도 예외를 전파하지 않는다")
    void withdrawUser_googleUnlinkFails_returnsGoogleFalse() throws Exception {
        // given
        Long id = 6L;
        String googleAccessToken = "google-token";
        User user = mock(User.class);
        UnlinkOauth2AccountFailedException exception = new UnlinkOauth2AccountFailedException("fail");

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);
        when(user.hasGoogleAccount()).thenReturn(true);
        doThrow(exception).when(oauth2AccountUnlinkPort).unlinkGoogleAccount(googleAccessToken);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, googleAccessToken);

        // then
        assertThat(result.isKakaoDisconnected()).isTrue();
        assertThat(result.isGoogleDisconnected()).isFalse();
        assertThat(result.isAppleDisconnected()).isTrue();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(oauth2AccountUnlinkPort).unlinkGoogleAccount(googleAccessToken);
        verify(user).removeGoogleOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort, oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 애플 계정이 있으면 연결 해제 결과가 실패로 반환된다")
    void withdrawUser_appleAccount_returnsAppleFalse() {
        // given
        Long id = 7L;
        User user = mock(User.class);

        when(getUserUseCase.getAllStatusUser(id)).thenReturn(user);
        when(user.hasAppleAccount()).thenReturn(true);

        // when
        WithdrawResult result = withdrawService.withdrawUser(id, null);

        // then
        assertThat(result.isKakaoDisconnected()).isTrue();
        assertThat(result.isGoogleDisconnected()).isTrue();
        assertThat(result.isAppleDisconnected()).isFalse();

        verify(getUserUseCase).getAllStatusUser(id);
        verify(user).withdraw();
        verify(user).removeAppleOidcId();
        verify(updateUserPort).update(user);
        verifyNoMoreInteractions(getUserUseCase, updateUserPort);
        verifyNoInteractions(oauth2AccountUnlinkPort);
    }

    @Test
    @DisplayName("회원 탈퇴 요청 시 회원 조회에 실패하면 예외를 던진다")
    void withdrawUser_userNotFound_throws() {
        // given
        Long id = 8L;
        UserNotFoundException exception = new UserNotFoundException("not found");

        when(getUserUseCase.getAllStatusUser(id)).thenThrow(exception);

        // expect
        assertThatThrownBy(() -> withdrawService.withdrawUser(id, "token"))
                .isSameAs(exception);

        verify(getUserUseCase).getAllStatusUser(id);
        verifyNoMoreInteractions(getUserUseCase);
        verifyNoInteractions(updateUserPort, oauth2AccountUnlinkPort);
    }
}
