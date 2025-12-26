package com.personal.marketnote.user.port.out;

import com.personal.marketnote.user.domain.user.User;

import java.util.Optional;

public interface FindUserPort {
    boolean isUserExists(String oidcId);

    Optional<User> findById(Long userId);

    Optional<User> findByOidcId(String oidcId);
}
