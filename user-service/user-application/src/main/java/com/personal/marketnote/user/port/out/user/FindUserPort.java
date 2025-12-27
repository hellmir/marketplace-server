package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

import java.util.Optional;

public interface FindUserPort {
    boolean existsByOidcId(String oidcId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findById(Long id);

    Optional<User> findByAuthVendorAndOidcId(AuthVendor authVendor, String oidcId);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
