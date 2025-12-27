package com.personal.marketnote.user.service.authentication.service;

import com.personal.marketnote.common.domain.exception.illegalargument.novalue.OauthTokenNoValueException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.in.result.LoginResult;
import com.personal.marketnote.user.port.in.usecase.LoginUseCase;
import com.personal.marketnote.user.port.out.FindUserPort;
import com.personal.marketnote.user.security.token.dto.GrantedTokenInfo;
import com.personal.marketnote.user.security.token.dto.OAuth2UserInfo;
import com.personal.marketnote.user.security.token.exception.UnsupportedCodeException;
import com.personal.marketnote.user.security.token.support.TokenSupport;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements LoginUseCase {
    private final TokenSupport tokenSupport;
    private final FindUserPort findUserPort;

    @Override
    @Transactional
    public LoginResult loginByOAuth2(String code, String redirectUri, AuthVendor authVendor)
            throws UnsupportedCodeException {
        GrantedTokenInfo grantedTokenInfo = tokenSupport.grantToken(code, redirectUri, authVendor);
        Optional<User> user = findUserPort.findByAuthVendorAndOidcId(grantedTokenInfo.authVendor(), grantedTokenInfo.id());

        if (user.isPresent()) {
            User signedUpUser = user.get();

            return LoginResult.of(
                    false, grantedTokenInfo.accessToken(), grantedTokenInfo.refreshToken(), signedUpUser.getNickname()
            );
        }

        OAuth2UserInfo userInfo = grantedTokenInfo.userInfo();

        if (!FormatValidator.hasValue(userInfo)) {
            throw new OauthTokenNoValueException();
        }

        return LoginResult.of(true, grantedTokenInfo.accessToken(), grantedTokenInfo.refreshToken(), userInfo.name());
    }
}


