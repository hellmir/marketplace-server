package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserSearchTarget;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FindUserPort {
    boolean existsByAuthVendorAndOidcId(AuthVendor authVendor, String oidcId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByReferenceCode(String referenceCode);

    Optional<User> findById(Long id);

    Optional<User> findByAuthVendorAndOidcId(AuthVendor authVendor, String oidcId);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findAllStatusUserById(Long id);

    Optional<User> findAllStatusUserByEmail(String email);

    List<User> findAllStatusUsers();

    Page<User> findAllStatusUsersByPage(Pageable pageable, UserSearchTarget searchTarget, String searchKeyword);
}
