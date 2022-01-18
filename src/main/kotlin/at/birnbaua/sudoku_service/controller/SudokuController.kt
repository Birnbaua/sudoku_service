package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.projection.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.jpa.entity.sudoku.validation.SudokuValidation
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/sudoku")
class SudokuController {

    val log: Logger = LoggerFactory.getLogger(SudokuController::class.java)

    @Autowired
    lateinit var ss: SudokuService

    @Autowired
    lateinit var sv: SudokuValidation

    @GetMapping
    fun all(@RequestParam(required = false) difficulty: Int?, @RequestParam(required = false) page: Int?, @RequestParam(required = false) size: Int?) : ResponseEntity<Page<SudokuInfo>> {
        return ResponseEntity.ok(ss.overview(difficulty,page,size))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) : ResponseEntity<SudokuGetInfo> {
        return ResponseEntity.ok(ss.findByIdGetInfo(id).orElseThrow { SudokuNotExistingException(id,log) })
    }

    @GetMapping("/{id}/validate")
    fun validate(@PathVariable id: Int, @RequestBody(required = false) solved: String?, @RequestParam(required = false, name = "solved") solvedParam: String?) : ResponseEntity<Boolean> {
        return if(solvedParam != null) {
            ResponseEntity.ok(sv.validate(id,solvedParam,ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }.type!!))
        } else if(solved != null) {
            ResponseEntity.ok(sv.validate(id,solved,ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }.type!!))
        } else {
            ResponseEntity.ok(false)
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun post(@RequestBody sudoku: Sudoku, auth: Authentication) : ResponseEntity<Sudoku> {
        sudoku.owner = User(auth.name)
        return ResponseEntity.ok(ss.save(sudoku))
    }

    @PreAuthorize("isAuthenticated() AND (hasRole('ROLE_ADMIN') OR @ownerChecker.isSudokuOwner(#id,#auth.name))")
    @PutMapping("/{id}")
    fun put(@PathVariable id: Int, @RequestBody sudoku: Sudoku) : ResponseEntity<Sudoku> {
        sudoku.id = id
        return ResponseEntity.ok(ss.save(sudoku))
    }

    @PreAuthorize("isAuthenticated() AND (hasRole('ROLE_ADMIN') OR @ownerChecker.isSudokuOwner(#id,#auth.name))")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, auth: Authentication) : ResponseEntity<Any> {
        ss.deleteById(id)
        return ResponseEntity.accepted().build()
    }
}