package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.jpa.jpaservice.GameStatsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/maintenance")
class MaintenanceController {

    @Autowired
    lateinit var gss: GameStatsService

    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @DeleteMapping
    fun deleteAllGameStats() : ResponseEntity<*> {
        return ResponseEntity.ok(gss.deleteAll())
    }

}