package at.birnbaua.sudoku_service.controller

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

    @PutMapping("/{sudoku}")
    fun save(@RequestBody stats: GameStats, @PathVariable sudoku: Int, request: HttpServletRequest) : ResponseEntity<GameStatsInfo> {
        stats.user = us.findUserByUsername(request.userPrincipal.name)
        stats.sudoku = Sudoku(sudoku)
        return ResponseEntity.status(HttpStatus.CREATED).body(gss.saveInfo(stats))
    }

    @GetMapping
    fun getAll(@RequestParam user: String, @RequestParam page: Int? = 0, @RequestParam size: Int? = 6) : ResponseEntity<Page<GameStatsInfo>> {
        return ResponseEntity.ok(gss.findGameStatsByUser(user,page!!,size!!))
    }

    @GetMapping("/{sudoku}/preview", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getPreview(@PathVariable sudoku: String, @RequestParam user: String) : ResponseEntity<ByteArray> {
        return ResponseEntity.ok(gss.findGameStatsByUser(user,0,1).first().preview)
    }
}