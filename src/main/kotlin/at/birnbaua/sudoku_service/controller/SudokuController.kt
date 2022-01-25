package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.projection.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.jpa.validation.SudokuValidator
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import at.birnbaua.sudoku_service.jpa.solver.Hint
import at.birnbaua.sudoku_service.jpa.solver.HintService
import at.birnbaua.sudoku_service.thymeleaf.SudokuPreviewService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * This controller is responsible for all  sudoku interaction.
 * The [RequestMapping] is /sudoku
 * @since 1.0
 * @author Andreas Bachl
 */
@CrossOrigin
@RestController
@RequestMapping("/sudoku")
class SudokuController {

    private val log: Logger = LoggerFactory.getLogger(SudokuController::class.java)

    @Autowired
    private lateinit var ss: SudokuService

    @Autowired
    private lateinit var sv: SudokuValidator

    @Autowired
    private lateinit var hs: HintService

    /**
     * @return A [Page] of the [SudokuInfo] projection.
     * @since 1.0
     * @param difficulty The id of the filtered [at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty]. If null then all difficulties are taken.
     * @param page The number of the requested page for pagination. If null then page=0 (First page)
     * @param size The number of the size of the requested page. If null then size=6
     */
    @GetMapping
    fun all(@RequestParam(required = false) difficulty: Int?, @RequestParam(required = false) page: Int?, @RequestParam(required = false) size: Int?) : ResponseEntity<Page<SudokuInfo>> {
        return ResponseEntity.ok(ss.overview(difficulty,page,size))
    }

    /**
     * @return The sudoku with the requested id
     * @since 1.0
     * @param id The id of the requested sudoku
     */
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) : ResponseEntity<SudokuGetInfo> {
        return ResponseEntity.ok(ss.findByIdGetInfo(id).orElseThrow { SudokuNotExistingException(id,log) })
    }

    /**
     * The sudoku can either be transmitted via the query parameter: ?solved=
     * or in a requestBody with the attribute 'solved'. Either one of these two must be present!
     * @return A boolean if the requested sudoku is valid or not
     * @since 1.0
     * @param id The id of the requested sudoku to be validated
     * @param solved RequestBody for transmitting the sudoku which should be validated
     * @param solvedParam Query parameter for transmitting the sudoku which should be validated. Use like following: ?solved=...
     */
    @GetMapping("/{id}/validate")
    fun validate(@PathVariable id: Int, @RequestBody(required = false) solved: String?, @RequestParam(required = false, name = "solved", defaultValue = "") solvedParam: String?,
                 @RequestParam(required = false, name = "type") type: SudokuType?) : ResponseEntity<Boolean> {
        return if(solvedParam != null && solvedParam.isNotEmpty()) {
            log.debug("Sudoku with solution: $solvedParam")
            if(type != null) {
                ResponseEntity.ok(sv.validate(id,solvedParam,type))
            } else {
                ResponseEntity.ok(sv.validate(id,solvedParam,ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }.type!!))
            }
        } else if(solved != null) {
            log.debug("Input in request body!")
            if(type != null) {
                ResponseEntity.ok(sv.validate(id,solved,type))
            } else {
                ResponseEntity.ok(sv.validate(id,solved,ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }.type!!))
            }
        } else {
            log.debug("No input received!")
            ResponseEntity.ok(false)
        }
    }

    /**
     * AT THE MOMENT, THE ID DOES NOT MATTER FOR CORRECT HINTS GIVEN!
     * @return A [Hint] if the given sudoku is solvable.
     * @since 1.0
     * @param id The id of the requested sudoku to be checked for giving hint.
     * @param sudoku Query parameter for transmitting the sudoku for getting a hint. Use like following: ?sudoku=...
     */
    @GetMapping("/{id}/hint")
    fun hint(@PathVariable id: Int, @RequestParam(required = true, name = "sudoku") sudoku: String) : ResponseEntity<Hint> {
        return ResponseEntity.ok(hs.getHint(id,sudoku))
    }

    /**
     * Creates a new sudoku
     * @return A [Sudoku] if the given sudoku is valid.
     * @since 1.0
     * @param sudoku RequestBody with the sudoku to be saved.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun post(@RequestBody sudoku: Sudoku, auth: Authentication) : ResponseEntity<Sudoku> {
        sudoku.owner = User(auth.name)
        return ResponseEntity.created(URI("/sudoku/${sudoku.id}")).body(ss.save(sudoku))
    }

    /**
     * Updates an existing sudoku or creates a new one if not present yet.
     * @return A [Sudoku] if the given sudoku is valid.
     * @since 1.0
     * @param id The id of the sudoku which should be modified or created.
     * @param sudoku RequestBody with the sudoku to be saved.
     */
    @PreAuthorize("isAuthenticated() AND (hasRole('ROLE_ADMIN') OR @ownerChecker.isSudokuOwner(#id,#auth.name))")
    @PutMapping("/{id}")
    fun put(@PathVariable id: Int, @RequestBody sudoku: Sudoku) : ResponseEntity<Sudoku> {
        sudoku.id = id
        return ResponseEntity.ok(ss.save(sudoku))
    }

    /**
     * Deletes a sudoku
     * @return nothing.
     * @since 1.0
     * @param id The id of the sudoku which should be deleted.
     */
    @PreAuthorize("isAuthenticated() AND (hasRole('ROLE_ADMIN') OR @ownerChecker.isSudokuOwner(#id,#auth.name))")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, auth: Authentication) : ResponseEntity<Any> {
        ss.deleteById(id)
        return ResponseEntity.accepted().build()
    }
}