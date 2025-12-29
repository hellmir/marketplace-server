package com.personal.marketnote.user.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.user.adapter.out.mapper.UserJpaEntityToDomainMapper;
import com.personal.marketnote.user.adapter.out.persistence.user.entity.UserJpaEntity;
import com.personal.marketnote.user.adapter.out.persistence.user.repository.TermsJpaRepository;
import com.personal.marketnote.user.adapter.out.persistence.user.repository.UserJpaRepository;
import com.personal.marketnote.user.domain.user.SearchTarget;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserNotFoundException;
import com.personal.marketnote.user.port.out.user.FindTermsPort;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.port.out.user.SaveUserPort;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.personal.marketnote.user.exception.ExceptionMessage.USER_ID_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, FindUserPort, FindTermsPort, UpdateUserPort {
    private final UserJpaRepository userJpaRepository;
    private final TermsJpaRepository termsJpaRepository;

    @Override
    public User save(User user) {
        UserJpaEntity userJpaEntity = UserJpaEntity.from(user, termsJpaRepository);

        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.save(userJpaEntity)).get();
    }

    @Override
    public boolean existsByAuthVendorAndOidcId(AuthVendor authVendor, String oidcId) {
        return userJpaRepository.existsByAuthVendorAndOidcId(authVendor, oidcId);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userJpaRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByReferenceCode(String referenceCode) {
        return userJpaRepository.existsByReferenceCode(referenceCode);
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
                userJpaRepository.findByAuthVendorAndOidcId(authVendor, oidcId).orElse(null));
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findByPhoneNumber(phoneNumber).orElse(null));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findByEmail(email).orElse(null));
    }

    @Override
    public Optional<User> findAllStatusUserById(Long id) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findAllStatusUserById(id).orElse(null));
    }

    @Override
    public Optional<User> findAllStatusUserByEmail(String email) {
        return UserJpaEntityToDomainMapper.mapToDomain(userJpaRepository.findAllStatusUserByEmail(email).orElse(null));
    }

    @Override
    public List<User> findAllStatusUsers() {
        return userJpaRepository.findAllStatusUsers().stream()
                .map(UserJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> findAllStatusUsersByPage(Pageable pageable, SearchTarget searchTarget, String searchKeyword) {
        boolean byId = searchTarget == SearchTarget.ID;
        boolean byNickname = searchTarget == SearchTarget.NICKNAME;
        boolean byEmail = searchTarget == SearchTarget.EMAIL;
        boolean byPhone = searchTarget == SearchTarget.PHONE_NUMBER;
        boolean byRefCode = searchTarget == SearchTarget.REFERENCE_CODE;

        Page<UserJpaEntity> userJpaEntityPage = userJpaRepository.findAllStatusUsersByPage(
                pageable, byId, byNickname, byEmail, byPhone, byRefCode, searchKeyword);

        List<User> users = userJpaEntityPage
                .stream()
                .map(UserJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return new PageImpl<>(users, pageable, userJpaEntityPage.getTotalElements());
    }

    @Override
    public List<Terms> findAll() {
        return termsJpaRepository.findAllByOrderByIdAsc().stream()
                .map(UserJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public void update(User user) {
        UserJpaEntity userJpaEntity = findEntityById(user.getId());
        userJpaEntity.updateFrom(user);
    }

    private UserJpaEntity findEntityById(Long id) {
        return userJpaRepository.findAllStatusUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, id)));
    }
}
