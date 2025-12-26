package com.personal.shop.user.utility.jwt;

import com.personal.shop.user.security.token.vendor.AuthVendor;
import com.personal.shop.user.utility.jwt.claims.TokenClaims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Profile("qa.test | prod")
public class JwtUtil {
    private final String ROLE_IDS_CLAIM_KEY = "roleIds";
    private final String TOKEN_TYPE_CLAIM_KEY = "tokenType";
    private final String AUTH_VENDOR_CLAIM_KEY = "authVendor";
    private final String USER_ID_CLAIM_KEY = "userId";

    private final SecretKey secretKey;
    private final JwtParser parser;
    private final Map<JwtTokenType, Long> ttlOfTokenType;

    public JwtUtil(@Value("${spring.jwt.secret}") String jwtSecret,
                   // 테스트가 쉬운 구조를 만들기 위해 TTL을 enum이 아닌 Spring properties에 정의하여 생성자로 주입
                   @Value("${spring.jwt.access-token.ttl}") Long accessTokenTtl,
                   @Value("${spring.jwt.refresh-token.ttl}") Long refreshTokenTtl) {
        this.secretKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        this.parser = Jwts.parser().verifyWith(secretKey)
                .json(new JacksonDeserializer<>())
                .build();
        this.ttlOfTokenType = Map.of(
                JwtTokenType.ACCESS_TOKEN, accessTokenTtl,
                JwtTokenType.REFRESH_TOKEN, refreshTokenTtl
        );
    }

    public TokenClaims parseAccessToken(String token)
            throws JwtException, IllegalArgumentException, ClassCastException {
        return parse(token, JwtTokenType.ACCESS_TOKEN);
    }

    public TokenClaims parseRefreshToken(String token) throws JwtException, IllegalArgumentException, ClassCastException {
        return parse(token, JwtTokenType.REFRESH_TOKEN);
    }

    private TokenClaims parse(String token, JwtTokenType tokenType) {
        Claims claims = this.parser.parseSignedClaims(token).getPayload();
        Object userIdObj = claims.get(USER_ID_CLAIM_KEY);
        Long userId = userIdObj == null ? null : ((Number) userIdObj).longValue();

        return TokenClaims.builder()
                .tokenType(extractTokenType(claims, tokenType))
                .expirationAt(claims.getExpiration()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .issuedAt(claims.getIssuedAt()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .id(claims.getSubject())
                .userId(userId)
                .roleIds((List<String>) claims.get("roleIds"))
                .authVendor(AuthVendor.valueOfIgnoreCase(claims.get(AUTH_VENDOR_CLAIM_KEY, String.class)))
                .build();
    }

    private JwtTokenType extractTokenType(Claims claims, JwtTokenType expectedTokenType) {
        JwtTokenType fromClaim = JwtTokenType.from(claims.get(TOKEN_TYPE_CLAIM_KEY, String.class));
        if (expectedTokenType != fromClaim) {
            throw new IllegalArgumentException(
                    String.format("Expected parsing %s, but just attempted to parse %s", expectedTokenType, fromClaim));
        }

        return fromClaim;
    }

    public String generateAccessToken(String id, List<String> roleIds, AuthVendor authVendor) {
        return generateAccessToken(id, null, roleIds, authVendor);
    }

    public String generateAccessToken(String id, Long userId, List<String> roleIds, AuthVendor authVendor) {
        validate(id, roleIds, authVendor);

        return getBuilderWithCommonClaims(JwtTokenType.ACCESS_TOKEN, id, userId, roleIds, authVendor).compact();
    }

    private void validate(String id, List<String> roleIds, AuthVendor authVendor) {
        List<String> messages = new ArrayList<>(2);
        if (id == null) {
            messages.add("memberId cannot be null");
        }

        if (ObjectUtils.isEmpty(roleIds)) {
            messages.add("roleIds cannot be empty");
        }

        if (authVendor == null) {
            messages.add("authVendor cannot be null");
        }

        if (!messages.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", messages.toArray(new String[0])));
        }
    }

    public String generateRefreshToken(String id, List<String> roleIds, AuthVendor authVendor) {
        return generateRefreshToken(id, null, roleIds, authVendor);
    }

    public String generateRefreshToken(String id, Long userId, List<String> roleIds, AuthVendor authVendor) {
        return getBuilderWithCommonClaims(JwtTokenType.REFRESH_TOKEN, id, userId, roleIds, authVendor).compact();
    }

    private JwtBuilder getBuilderWithCommonClaims(
            JwtTokenType tokenType,
            String id,
            Long userId,
            List<String> roleIds,
            AuthVendor authVendor
    ) {
        return Jwts.builder()
                .claim(TOKEN_TYPE_CLAIM_KEY, tokenType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + this.ttlOfTokenType.get(tokenType)))
                .subject(id)
                .claim(ROLE_IDS_CLAIM_KEY, roleIds)
                .claim(USER_ID_CLAIM_KEY, userId)
                .claim(AUTH_VENDOR_CLAIM_KEY, authVendor.name())
                .signWith(this.secretKey);
    }
}
