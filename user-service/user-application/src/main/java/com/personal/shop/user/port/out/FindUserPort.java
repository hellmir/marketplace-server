package com.personal.shop.user.port.out;

import com.personal.shop.user.domain.user.User;

import java.util.Optional;

public interface FindUserPort {
    boolean isUserExists(String oidcId);

    Optional<User> findById(Long userId);

    Optional<User> findByOidcId(String oidcId);
}
