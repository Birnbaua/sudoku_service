package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.jpaservice.GameStatsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin
@RestController
@RequestMapping("/gamestats")
class GameStatsController {

    @Autowired
    lateinit var gss: GameStatsService

    @Autowired
    lateinit var us: UserService

    @PreAuthorize("isAuthenticated() AND (hasRole('ROLE_ADMIN') OR #auth.name==#username)")
    @PutMapping("/{username}/{sudoku}")
    fun save(@RequestBody stats: GameStats, @PathVariable username: String, @PathVariable sudoku: Int, auth: Authentication) : ResponseEntity<GameStatsInfo> {
        stats.user = User(username)
        stats.sudoku = Sudoku(sudoku)
        return ResponseEntity.status(HttpStatus.CREATED).body(gss.saveInfo(stats))
    }

    @GetMapping("/{username}")
    fun getAll(@PathVariable username: String, @RequestParam page: Int? = 0, @RequestParam size: Int? = 6) : ResponseEntity<Page<GameStatsInfo>> {
        return ResponseEntity.ok(gss.findGameStatsByUser(username,page!!,size!!))
    }

    @GetMapping("/{username}/{sudoku}/preview", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getPreview(@PathVariable sudoku: String, @PathVariable username: String) : ResponseEntity<ByteArray> {
        return ResponseEntity.ok(gss.findGameStatsByUser(username,0,1).first().preview)
    }
}