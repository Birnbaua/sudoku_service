package at.birnbaua.sudoku_service.auth.jwt

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class AuthenticationController {

    @PostMapping("/login")
    fun login() : ResponseEntity {
        return ResponseEntity<>(authenticationService.generateJWTToken(request.getUsername(), request.getPassword()), HttpStatus.OK)
    }
}