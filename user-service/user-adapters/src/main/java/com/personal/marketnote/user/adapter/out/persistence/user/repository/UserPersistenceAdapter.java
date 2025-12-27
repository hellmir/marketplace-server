package com.personal.marketnote.user.adapter.out.persistence.user.repository;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.user.adapter.out.mapper.UserJpaEntityToDomainMapper;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.out.FindUserPort;
import com.personal.marketnote.user.port.out.GetUserTermsPort;
import com.personal.marketnote.user.port.out.SignUpPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, propagation = REQUIRES_NEW, timeout = 180)
public class UserPersistenceAdapter implements SignUpPort, FindUserPort, GetUserTermsPort {
    private final UserJpaRepository userJpaRepository;
    private final TermsJpaRepository termsJpaRepository;

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
    public Optional<User> findByOidcId(String oidcId) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findByOidcId(oidcId).orElse(null));
    }

    @Override
    public List<Terms> getAllTerms() {
        return termsJpaRepository.findAll().stream()
                .map(UserJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
