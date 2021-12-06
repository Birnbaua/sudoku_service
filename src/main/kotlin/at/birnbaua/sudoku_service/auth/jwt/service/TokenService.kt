package at.birnbaua.sudoku_service.auth.jwt.service

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

//    @Value(value = "\${jwt.prefix}")
    var prefix: String = "Bearer "

    init {
        prefix = prefix.replace("\"","")
    }

    fun genToken(username: String): String {
        val creationDate = Date()
        val expirationDate = calcExpireDate(creationDate)
        return prefix + Jwts.builder()
            .setClaims(mutableMapOf())
            .setSubject(username)
            .setIssuedAt(creationDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun validateToken(token: String) : Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            println(getExpiryDateFromToken(token).toString())
            if(getExpiryDateFromToken(token).after(Date())) {
                return true
            }
        } catch(e: Exception) {}
        return false
    }

    fun getExpiryDateFromToken(token: String?): Date {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.expiration
    }

    fun getUsernameFromToken(token: String?): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    private fun calcExpireDate(creationDate: Date) : Date {
        return Date(creationDate.time + expiration.toLong() * 1000)
    }
}