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

@CrossOrigin
@RestController
@RequestMapping("/auth")
@EnableConfigurationProperties(JwtProperties::class)
class AuthenticationController {

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var jwtProperties: JwtProperties

    @PostMapping("/authenticate")
    fun getToken(@RequestBody request: AuthRequest) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.genToken(request.username,request.password))
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    fun getToken(request: HttpServletRequest, @RequestBody token: JWTToken) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.refresh(request.getHeader(jwtProperties.header).replace(jwtProperties.prefix,"")))
    }
}