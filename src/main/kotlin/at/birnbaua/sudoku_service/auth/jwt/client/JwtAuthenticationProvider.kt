package at.birnbaua.sudoku_service.auth.jwt.client

import at.birnbaua.sudoku_service.auth.jwt.client.service.TokenServiceClient
import at.birnbaua.sudoku_service.exception.JwtAuthenticationException
import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider : AuthenticationProvider {
    private val log: Logger = LoggerFactory.getLogger(JwtAuthenticationProvider::class.java)

    @Autowired
    lateinit var jwtService: TokenServiceClient

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        return try {
            val token = authentication.credentials as String
            val username: String = jwtService.getUsernameFromToken(token)
            jwtService.validateToken(token)
                .map { JwtAuthentication(username) }
                .orElseThrow { JwtAuthenticationException("JWT Token validation failed") }
        } catch (ex: JwtException) {
            log.error(String.format("Invalid JWT Token: %s", ex.message))
            throw JwtAuthenticationException("Failed to verify token")
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return JwtAuthentication::class.java == authentication
    }
}