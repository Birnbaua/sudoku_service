package at.birnbaua.sudoku_service.auth.jwt.client.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*


@Service
class TokenServiceClient {

    @Value(value = "\${jwt.secret}")
    lateinit var secret: String


    fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getAllClaimsFromToken(token).expiration
    }

    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenNotExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.after(Date())
    }

    fun validateToken(token: String): Optional<Boolean> {
        return if (isTokenNotExpired(token)) Optional.of(java.lang.Boolean.TRUE) else Optional.empty()
    }

}