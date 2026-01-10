package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class LoginHistory {
    private Long id;
    private User user;
    private AuthVendor authVendor;
    private String ipAddress;
    private LocalDateTime createdAt;

    public static LoginHistory from(LoginHistoryCreateState state) {
        return LoginHistory.builder()
                .user(state.getUser())
                .authVendor(state.getAuthVendor())
                .ipAddress(state.getIpAddress())
                .build();
    }

    public static LoginHistory from(LoginHistorySnapshotState state) {
        return LoginHistory.builder()
                .id(state.getId())
                .user(state.getUser())
                .authVendor(state.getAuthVendor())
                .ipAddress(state.getIpAddress())
                .createdAt(state.getCreatedAt())
                .build();
    }
}
