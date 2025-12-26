package com.personal.shop.user.adapter.out.persistence.user.repository;

import com.personal.shop.user.adapter.out.mapper.UserJpaEntityToDomainMapper;
import com.personal.shop.user.adapter.out.persistence.user.entity.QUserJpaEntity;
import com.personal.shop.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.shop.user.domain.user.User;
import com.personal.shop.user.port.out.FindUserPort;
import com.personal.shop.user.port.out.SignUpPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SignUpPort, FindUserPort {
    private final UserJpaRepository userJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final QUserJpaEntity user = QUserJpaEntity.userJpaEntity;

    @Override
    public User saveUser(User user) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.save(UserJpaEntity.from(user))).get();
    }

    @Override
    public boolean isUserExists(String oidcId) {
        return userJpaRepository.existsByOidcId(oidcId);
    }

    @Override
    public Optional<User> findById(Long id) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findById(id).orElse(null));
    }

    @Override
    public Optional<User> findByOidcId(String oidcId) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findByOidcId(oidcId).orElse(null));
    }
}
