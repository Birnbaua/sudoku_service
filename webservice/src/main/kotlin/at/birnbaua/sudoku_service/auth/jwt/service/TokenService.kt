package at.birnbaua.sudoku_service.auth.jwt.service

import at.birnbaua.sudoku_service.auth.jwt.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

/**
 * This service handles everything concerning JWTs.
 * @since 1.0
 * @author Andreas Bachl
 */
@Service
@EnableConfigurationProperties(JwtProperties::class)
class TokenService {

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    /**
     * Generating a JWT for given username.
     * @return a valid JWT
     * @since 1.0
     */
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

    /**
     * Check if token has a valid format and the expiry date is in the future
     * @return a valid JWT
     * @since 1.0
     */
    fun validateToken(token: String) : Boolean {
        try {
            Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token)
            //println(getExpiryDateFromToken(token).toString())
            if(getExpiryDateFromToken(token).after(Date())) {
                return true
            }
        } catch(e: Exception) {}
        return false
    }

    /**
     * @return the expiry date of the token
     * @since 1.0
     */
    fun getExpiryDateFromToken(token: String?): Date {
        return Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body.expiration
    }

    /**
     * @return the username of the token
     * @since 1.0
     */
    fun getUsernameFromToken(token: String?): String {
        return Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body.subject
    }

    /**
     * @return the expiry date of the token calculated with the current time and how long a token should be valid.
     * @since 1.0
     */
    private fun calcExpireDate(creationDate: Date) : Date {
        return Date(creationDate.time + jwtProperties.expiryTime)
    }
}