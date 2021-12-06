package at.birnbaua.sudoku_service.auth.controller

import at.birnbaua.sudoku_service.auth.jwt.AuthRequest
import at.birnbaua.sudoku_service.auth.jwt.JWTToken
import at.birnbaua.sudoku_service.auth.jwt.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/auth")
class AuthenticationController {

    @Autowired
    lateinit var authService: AuthService

    @PostMapping("/authenticate")
    fun getToken(@RequestBody request: AuthRequest) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.genToken(request.username,request.password))
    }

    @PostMapping("/refresh")
    fun getToken(@RequestBody token: JWTToken) : ResponseEntity<JWTToken> {
        return ResponseEntity.ok(authService.refresh(token))
    }
}