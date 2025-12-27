package com.personal.marketnote.user.adapter.out.persistence;

import com.personal.marketnote.user.adapter.out.mapper.UserJpaEntityToDomainMapper;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.repository.UserJpaRepository;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.out.FindUserPort;
import com.personal.marketnote.user.port.out.SignUpPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, propagation = REQUIRES_NEW, timeout = 180)
public class UserPersistenceAdapter implements SignUpPort, FindUserPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User saveUser(User user) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.save(UserJpaEntity.from(user))).get();
    }

    @Override
    public boolean existsByOidcId(String oidcId) {
        return userJpaRepository.existsByOidcId(oidcId);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userJpaRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional(isolation = READ_UNCOMMITTED, readOnly = true, timeout = 120)
    public Optional<User> findById(Long id) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(isolation = READ_UNCOMMITTED, readOnly = true, timeout = 120)
    public Optional<User> findByAuthVendorAndOidcId(AuthVendor authVendor, String oidcId) {
        return UserJpaEntityToDomainMapper.mapToDomain(
                userJpaRepository.findByAuthVendorAndOidcId(authVendor, oidcId).orElse(null)
        );
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findByPhoneNumber(phoneNumber).orElse(null));
    }
}
