package com.personal.marketnote.user.port.in.mapper;

import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserCreateState;
import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCommandToDomainMapper {
    public static User mapToDomain(
            SignUpCommand command,
            AuthVendor authVendor,
            String oidcId,
            List<Terms> terms,
            String referenceCode,
            PasswordEncoder passwordEncoder
    ) {
        String encodedPassword = null;
        if (authVendor.isNative() && command.hasPassword()) {
            encodedPassword = passwordEncoder.encode(command.getPassword());
        }

        return User.from(
                UserCreateState.builder()
                        .authVendor(authVendor)
                        .oidcId(oidcId)
                        .nickname(command.getNickname())
                        .email(command.getEmail())
                        .encodedPassword(encodedPassword)
                        .fullName(command.getFullName())
                        .phoneNumber(command.getPhoneNumber())
                        .terms(terms)
                        .referenceCode(referenceCode)
                        .build()
        );
    }
}
