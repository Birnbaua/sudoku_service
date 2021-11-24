package at.birnbaua.sudoku_service.auth.jwt.server.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value(value = "\${jwt.secret}")
    lateinit var secret: String

    @Value(value = "\${jwt.expiration}")
    lateinit var expiration: String

    fun genToken(username: String): String {
        val creationDate = Date()
        val expirationDate = calcExpireDate(creationDate)
        return Jwts.builder()
            .setClaims(mutableMapOf())
            .setSubject(username)
            .setIssuedAt(creationDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    private fun calcExpireDate(creationDate: Date) : Date {
        return Date(creationDate.time + expiration.toLong() * 10000)
    }
}