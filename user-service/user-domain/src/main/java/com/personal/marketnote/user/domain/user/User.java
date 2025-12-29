package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.domain.exception.illegalstate.SameUpdateTargetException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class User {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String referenceCode;
    private String referredUserCode;
    private Role role;
    private List<UserOauth2Vendor> userOauth2Vendors;
    private List<UserTerms> userTerms;
    private final LocalDateTime lastLoggedInAt;
    private EntityStatus status;
    private boolean withdrawalYn;

    public static User from(AuthVendor authVendor, String oidcId) {
        return User.builder()
                .userOauth2Vendors(List.of(UserOauth2Vendor.of(authVendor, oidcId)))
                .role(Role.getGuest())
                .build();
    }

    public static User from(
            AuthVendor targetAuthVendor,
            String oidcId,
            String nickname,
            String email,
            String password,
            PasswordEncoder passwordEncoder,
            String fullName,
            String phoneNumber,
            List<Terms> terms,
            String referenceCode
    ) {
        List<UserOauth2Vendor> userOauth2Vendors = new ArrayList<>(AuthVendor.size());
        AuthVendor[] allAuthVendors = AuthVendor.values();

        for (AuthVendor authVendor : allAuthVendors) {
            UserOauth2Vendor userOauth2Vendor = UserOauth2Vendor.of(authVendor);
            userOauth2Vendors.add(userOauth2Vendor);

            if (authVendor.isMe(targetAuthVendor)) {
                userOauth2Vendor.addOidcId(targetAuthVendor, oidcId, email);
            }
        }

        User user = User.builder()
                .userOauth2Vendors(userOauth2Vendors)
                .nickname(nickname)
                .email(email)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .role(Role.getBuyer())
                .lastLoggedInAt(LocalDateTime.now())
                .build();

        if (targetAuthVendor.isNative() && FormatValidator.hasValue(password)) {
            user.password = passwordEncoder.encode(password);
        }

        user.userTerms = terms.stream()
                .map(term -> UserTerms.of(user, term))
                .collect(Collectors.toList());

        return user;
    }

    public static User from(
            Long id,
            String nickname,
            String email,
            String password,
            String fullName,
            String phoneNumber,
            String referenceCode,
            String referredUserCode,
            Role role,
            List<UserOauth2Vendor> userOauth2Vendors,
            List<UserTerms> userTerms,
            LocalDateTime lastLoggedInAt,
            EntityStatus status,
            boolean withdrawalYn
    ) {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .password(password)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .referredUserCode(referredUserCode)
                .role(role)
                .userOauth2Vendors(userOauth2Vendors)
                .userTerms(userTerms)
                .lastLoggedInAt(lastLoggedInAt)
                .status(status)
                .withdrawalYn(withdrawalYn)
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

    public void updateStatus(boolean isActive) {
        status = EntityStatus.from(isActive);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updatePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void registerReferredUserCode(String referredUserCode) {
        if (FormatValidator.hasValue(this.referredUserCode)) {
            throw new ReferredUserCodeAlreadyExistsException(SECOND_ERROR_CODE);
        }

        this.referredUserCode = referredUserCode;
    }

    public void registerEmail(String email) {
        this.email = email;
    }

    public void addLoginAccountInfo(AuthVendor authVendor, String oidcId, String password, PasswordEncoder passwordEncoder) {
        userOauth2Vendors.forEach(
                userOauth2Vendor -> userOauth2Vendor.update(authVendor, oidcId)
        );

        if (FormatValidator.hasValue(password)) {
            this.password = passwordEncoder.encode(password);
        }
    }

    public void validateDifferentEmail(String email) {
        if (FormatValidator.equals(this.email, email)) {
            throw new SameUpdateTargetException(FIRST_ERROR_CODE, email);
        }
    }

    public void validateDifferentNickname(String nickname) {
        if (FormatValidator.equals(this.nickname, nickname)) {
            throw new SameUpdateTargetException(SECOND_ERROR_CODE, nickname);
        }
    }

    public void validateDifferentPhoneNumber(String phoneNumber) {
        if (FormatValidator.equals(this.phoneNumber, phoneNumber)) {
            throw new SameUpdateTargetException(THIRD_ERROR_CODE, phoneNumber);
        }
    }

    public void activate() {
        status = EntityStatus.ACTIVE;
    }

    public boolean isActive() {
        return status.isActive();
    }

    public boolean isRequiredTermsAgreed() {
        return userTerms.stream()
                .allMatch(UserTerms::isRequiredTermsAgreed);
    }

    public void withdraw() {
        withdrawalYn = true;
        status = EntityStatus.INACTIVE;
    }

    public void cancelWithdrawal() {
        withdrawalYn = false;
    }

    public boolean isWithdrawn() {
        return withdrawalYn;
    }

    public String getKakaoOidcId() {
        return userOauth2Vendors.stream()
                .filter(UserOauth2Vendor::isKakao)
                .map(UserOauth2Vendor::getOidcId)
                .findFirst()
                .orElse(null);
    }

    public void removeKakaoOidcId() {
        userOauth2Vendors.stream()
                .filter(UserOauth2Vendor::isKakao)
                .forEach(UserOauth2Vendor::removeOidcId);
    }

    public boolean hasGoogleAccount() {
        return userOauth2Vendors.stream()
                .anyMatch(UserOauth2Vendor::hasGoogleAccount);
    }

    public void removeGoogleOidcId() {
        userOauth2Vendors.stream()
                .filter(UserOauth2Vendor::isGoogle)
                .forEach(UserOauth2Vendor::removeOidcId);
    }

    public boolean hasAppleAccount() {
        return userOauth2Vendors.stream()
                .anyMatch(UserOauth2Vendor::hasAppleAccount);
    }

    public void removeAppleOidcId() {
        userOauth2Vendors.stream()
                .filter(UserOauth2Vendor::isApple)
                .forEach(UserOauth2Vendor::removeOidcId);
    }
}
