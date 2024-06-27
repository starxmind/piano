package com.starxmind.piano.jwt;

import com.starxmind.bass.json.XJson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具
 *
 * @author pizzalord
 * @since 1.0
 */
public abstract class JwtUtils {
    public static String encode(Object object, String secret, long expiration) {
        Map claims = object instanceof Map ? (Map) object : XJson.objectToMap(object);
        Date now = new Date();
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return jwt;
    }

    public static Map<String, Object> decode(String jwt, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static <T> T decode(String jwt, String secret, Class<T> clazz) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt).getBody();
        return XJson.mapToObject(claims, clazz);
    }
}
