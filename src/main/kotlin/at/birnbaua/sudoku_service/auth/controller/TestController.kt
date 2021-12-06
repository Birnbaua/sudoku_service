package at.birnbaua.sudoku_service.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class TestController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    fun getTest() : ResponseEntity<String> {
        return ResponseEntity.ok("Eureka!")
    }

    @PreAuthorize("isAuthenticated() AND hasRole('ADMIN')")
    @GetMapping("/admin")
    fun getTestAdmin(auth: Authentication) : ResponseEntity<String> {
        return ResponseEntity.ok("Eureka!")
    }
}