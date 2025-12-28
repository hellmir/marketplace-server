package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.common.domain.exception.illegalargument.novalue.UpdateTargetNoValueException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.exception.ReferredUserCodeAlreadyExistsException;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class User {
    private Long id;
    private AuthVendor authVendor;
    private String oidcId;
    private String nickname;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String referenceCode;
    private String referredUserCode;
    private Role role;
    private List<UserTerms> userTerms;
    private LocalDateTime lastLoggedInAt;

    public static User of(AuthVendor authVendor, String oidcId) {
        return User.builder()
                .authVendor(authVendor)
                .oidcId(oidcId)
                .role(Role.getGuest())
                .build();
    }

    public static User of(
            AuthVendor authVendor,
            String oidcId,
            String nickname,
            String email,
            String password,
            String fullName,
            String phoneNumber,
            List<Terms> terms,
            String referenceCode
    ) {
        User user = User.builder()
                .authVendor(authVendor)
                .oidcId(oidcId)
                .nickname(nickname)
                .email(email)
                .password(password)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .role(Role.getBuyer())
                .lastLoggedInAt(LocalDateTime.now())
                .build();

        user.userTerms = terms.stream()
                .map(term -> UserTerms.of(user, term))
                .collect(Collectors.toList());

        return user;
    }

    public static User of(
            Long id,
            AuthVendor authVendor,
            String oidcId,
            String nickname,
            String email,
            String password,
            String fullName,
            String phoneNumber,
            String referenceCode,
            String referredUserCode,
            Role role,
            List<UserTerms> userTerms,
            LocalDateTime lastLoggedInAt
    ) {
        return User.builder()
                .id(id)
                .authVendor(authVendor)
                .oidcId(oidcId)
                .nickname(nickname)
                .email(email)
                .password(password)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .referredUserCode(referredUserCode)
                .role(role)
                .userTerms(userTerms)
                .lastLoggedInAt(lastLoggedInAt)
                .build();
    }

    public boolean isGuest() {
        return role.isGuest();
    }

    public void acceptOrCancelTerms(List<Long> termsIds) {
        userTerms.stream()
                .filter(userTerms -> termsIds.contains(userTerms.getTerms().getId()))
                .forEach(UserTerms::acceptOrCancel);
    }

    public boolean isValidPassword(PasswordEncoder passwordEncoder, String targetPassword) {
        return passwordEncoder.matches(targetPassword, password);
    }

    public void update(
            String email, String nickname, String phoneNumber, String password, PasswordEncoder passwordEncoder
    ) throws UpdateTargetNoValueException {
        if (FormatValidator.hasValue(email)) {
            this.email = email;
            return;
        }

        if (FormatValidator.hasValue(nickname)) {
            this.nickname = nickname;
            return;
        }

        if (FormatValidator.hasValue(phoneNumber)) {
            this.phoneNumber = phoneNumber;
            return;
        }

        if (FormatValidator.hasValue(password)) {
            this.password = passwordEncoder.encode(password);
            return;
        }

        throw new UpdateTargetNoValueException();
    }

    public void registerReferredUserCode(String referredUserCode) throws ReferredUserCodeAlreadyExistsException {
        if (FormatValidator.hasValue(this.referredUserCode)) {
            throw new ReferredUserCodeAlreadyExistsException();
        }

        this.referredUserCode = referredUserCode;
    }
}
