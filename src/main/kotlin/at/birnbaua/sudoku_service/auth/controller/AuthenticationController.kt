package at.birnbaua.sudoku_service.auth.controller

import at.birnbaua.sudoku_service.auth.jwt.AuthRequest
import at.birnbaua.sudoku_service.auth.jwt.JWTToken
import at.birnbaua.sudoku_service.auth.jwt.service.AuthService
import at.birnbaua.sudoku_service.auth.jwt.properties.JwtProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * This controller is responsible for the JWT generation and the refresh
 * The [RequestMapping] is /auth
 * @since 1.0
 * @author Andreas Bachl
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
@EnableConfigurationProperties(JwtProperties::class)
class AuthenticationController {

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    /**
     * @return a valid JWT
     * @since 1.0
     * @throws [at.birnbaua.sudoku_service.auth.exception.UserNotFoundException]
     */
    @PostMapping("/authenticate")
    fun getToken(@RequestBody request: AuthRequest) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.genToken(request.username,request.password))
    }

    /**
     * Refresh a JWT token with an existing, valid JWT token
     * @return a valid JWT
     * @since 1.0
     * @throws [at.birnbaua.sudoku_service.auth.exception.TokenTimeoutException]
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    fun getToken(request: HttpServletRequest, @RequestBody token: JWTToken) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.refresh(request.getHeader(jwtProperties.header).replace(jwtProperties.prefix,"")))
    }
}