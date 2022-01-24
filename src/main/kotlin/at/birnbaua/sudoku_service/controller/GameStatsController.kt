package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.jpaservice.GameStatsService
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo2
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.thymeleaf.SudokuPreviewService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * This controller is responsible for all saving and getting the current stats of sudokus for the users.
 * The [RequestMapping] is /gamestats
 * @since 1.0
 * @author Andreas Bachl
 */
@CrossOrigin
@RestController
@RequestMapping("/gamestats")
class GameStatsController {

    private val log = LoggerFactory.getLogger(GameStatsController::class.java)

    @Autowired
    private lateinit var gss: GameStatsService

    @Autowired
    private lateinit var sps: SudokuPreviewService

    /**
     * @return The the stored [GameStats]
     * @since 1.0
     * @param stats The RequestBody with the GameStats to be saved
     * @param username The username for which user the stats should be saved
     * @param sudoku The id of the sudoku which should be referenced with the given GameStats
     */
    @PreAuthorize("isAuthenticated() AND (hasRole('ROLE_ADMIN') OR #auth.name==#username)")
    @PutMapping("/{username}/{sudoku}")
    fun save(@RequestBody @Valid stats: GameStats, @PathVariable username: String, @PathVariable sudoku: Int, auth: Authentication) : ResponseEntity<GameStatsInfo> {
        stats.user = User(username)
        stats.sudoku = Sudoku(sudoku)
        stats.preview = sps.toImage(sps.parseThymeleafTemplate(stats.currentResult!!))
        return ResponseEntity.status(HttpStatus.CREATED).body(gss.saveInfo(stats))
    }

    /**
     * @return A [Page] with a specified number of GameStats-objects
     * @since 1.0
     * @param username The username of the user the GameStats are requested of.
     * @param page The number of the requested page for pagination. If null then page=0 (First page)
     * @param size The number of the size of the requested page. If null then size=6
     */
    @GetMapping("/{username}")
    fun getAll(@PathVariable username: String, @RequestParam(required = false, defaultValue = "0") page: Int?, @RequestParam(required = false, defaultValue = "6") size: Int?) : ResponseEntity<Page<GameStatsInfo>> {
        log.info("Request with username: $username | page: $page | size: $size")
        return ResponseEntity.ok(gss.findGameStatsByUser(username,page!!,size!!))
    }

    /**
     * THIS API CAN BE USED BY NOT RECOMMENDED SINCE IT WAS ONLY FOR TESTING PURPOSES!
     * @return The preview picture of the saved GameStats sudoku.
     * @since 1.0
     * @param username The username of the user the GameStats-preview is requested of.
     * @param sudoku The id of the sudoku where the GameStats-preview is requested of.
     */
    @GetMapping("/{username}/{sudoku}/preview", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getPreview(@PathVariable sudoku: String, @PathVariable username: String) : ResponseEntity<ByteArray> {
        return ResponseEntity.ok(gss.findGameStatsByUser(username,0,1).first().preview)
    }
}