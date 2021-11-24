package at.birnbaua.sudoku_service.auth.jwt.server.controller

import at.birnbaua.sudoku_service.auth.jwt.server.AuthRequest
import at.birnbaua.sudoku_service.auth.jwt.server.JWTToken
import at.birnbaua.sudoku_service.auth.jwt.server.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class AuthenticationController {

    @Autowired
    lateinit var authService: AuthService

    @PostMapping("/token")
    fun obtainToken(@RequestBody authRequest: AuthRequest) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.genToken(authRequest.username,authRequest.password))
    }
}