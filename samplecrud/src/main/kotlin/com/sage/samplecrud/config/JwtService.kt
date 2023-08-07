package com.sage.samplecrud.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService {
    companion object {
        const val SECRET_KEY: String = "e2d2d43ff9ab206a21476ff3fe71db5ebc9501c2be6503925be9c8a18e0b5005";
    }

    fun extractUserName(jwt: String): String {
        return extractClaim(jwt, Claims::getSubject)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun generateToken(
        userDetails: UserDetails
    ): String {
        return generateToken(mutableMapOf<String, Any>(), userDetails)
    }

    private fun generateToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails
    ): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun isTokenValid(
        token: String,
        userDetails: UserDetails
    ): Boolean {
        val userName: String = extractUserName(token)
        return (userName.equals(userDetails.username)) && !isTokenExpired(token);
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token)
            .before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private fun getSigningKey(): Key {
        val keyBytes: ByteArray = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
