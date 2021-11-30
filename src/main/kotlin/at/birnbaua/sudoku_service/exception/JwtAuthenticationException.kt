package at.birnbaua.sudoku_service.exception

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(msg: String) : AuthenticationException(msg) {
}