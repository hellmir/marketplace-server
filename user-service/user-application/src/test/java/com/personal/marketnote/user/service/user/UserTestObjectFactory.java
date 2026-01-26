package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.domain.user.UserOauth2Vendor;
import com.personal.marketnote.user.domain.user.UserSnapshotState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

final class UserTestObjectFactory {
    private static final UUID DEFAULT_USER_KEY = UUID.fromString("00000000-0000-0000-0000-000000000001");

    private UserTestObjectFactory() {
    }

    static User createDefaultUser(
            Long id,
            EntityStatus status,
            boolean withdrawalYn,
            List<UserOauth2Vendor> userOauth2Vendors
    ) {
        return createUser(
                id,
                "tester",
                "user@test.com",
                "홍길동",
                "010-1111-2222",
                "ref-123",
                Role.getBuyer(),
                userOauth2Vendors,
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 2, 11, 0),
                status,
                withdrawalYn,
                5L
        );
    }

    static User createUser(
            Long id,
            String nickname,
            String email,
            String fullName,
            String phoneNumber,
            String referenceCode,
            Role role,
            List<UserOauth2Vendor> userOauth2Vendors,
            LocalDateTime signedUpAt,
            LocalDateTime lastLoggedInAt,
            EntityStatus status,
            boolean withdrawalYn,
            Long orderNum
    ) {
        UserSnapshotState state = UserSnapshotState.builder()
                .id(id)
                .userKey(DEFAULT_USER_KEY)
                .nickname(nickname)
                .email(email)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .role(role)
                .userOauth2Vendors(userOauth2Vendors)
                .userTerms(List.of())
                .signedUpAt(signedUpAt)
                .lastLoggedInAt(lastLoggedInAt)
                .status(status)
                .withdrawalYn(withdrawalYn)
                .orderNum(orderNum)
                .build();

        return User.from(state);
    }
}
