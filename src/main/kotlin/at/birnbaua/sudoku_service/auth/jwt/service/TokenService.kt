package at.birnbaua.sudoku_service.auth.jwt.service

import at.birnbaua.sudoku_service.auth.jwt.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.util.*

@Service
@EnableConfigurationProperties(JwtProperties::class)
class TokenService {

    @Autowired
    lateinit var jwtProperties: JwtProperties

    fun genToken(username: String): String {
        val creationDate = Date()
        val expirationDate = calcExpireDate(creationDate)
        return jwtProperties.prefix + Jwts.builder()
            .setClaims(mutableMapOf())
            .setSubject(username)
            .setIssuedAt(creationDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
            .compact()
    }

    fun validateToken(token: String) : Boolean {
        try {
            Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token)
            println(getExpiryDateFromToken(token).toString())
            if(getExpiryDateFromToken(token).after(Date())) {
                return true
            }
        } catch(e: Exception) {}
        return false
    }

    fun getExpiryDateFromToken(token: String?): Date {
        return Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body.expiration
    }

    fun getUsernameFromToken(token: String?): String {
        return Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body.subject
    }

    private fun calcExpireDate(creationDate: Date) : Date {
        return Date(creationDate.time + jwtProperties.expiryTime)
    }
}