package com.personal.marketnote.user.adapter.out.persistence.user.repository.dto;

import java.time.LocalDateTime;

public record UserSearchResult(
        Long userId,
        String userName,
        LocalDateTime joinedAt,
        LocalDateTime loginMostRecentlyAt,
        String roleId,
        String roleName
) {
}
