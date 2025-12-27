package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

import java.util.Optional;

public interface FindUserPort {
    boolean existsByOidcId(String oidcId);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNickname(String nickname);

    Optional<User> findById(Long id);

    Optional<User> findByAuthVendorAndOidcId(AuthVendor authVendor, String oidcId);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
