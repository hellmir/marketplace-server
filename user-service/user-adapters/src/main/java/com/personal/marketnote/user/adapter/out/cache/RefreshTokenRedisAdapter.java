package com.personal.marketnote.user.adapter.out.cache;

import com.personal.marketnote.user.port.out.authentication.DeleteRefreshTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisAdapter implements DeleteRefreshTokenPort {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void deleteByUserId(Long userId) {
        String key = "refreshToken:" + userId;
        stringRedisTemplate.delete(key);
    }
}


