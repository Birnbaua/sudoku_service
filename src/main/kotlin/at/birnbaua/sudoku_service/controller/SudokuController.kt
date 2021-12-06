package at.birnbaua.sudoku_service.controller

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
            ResponseEntity.ok(sv.validate(id,solvedParam))
        } else if(solved != null) {
            ResponseEntity.ok(sv.validate(id,solved))
        } else {
            ResponseEntity.ok(false)
        }
    }

    @PostMapping
    fun post(@RequestBody sudoku: Sudoku) : ResponseEntity<Sudoku> {
        return ResponseEntity.ok(ss.save(sudoku))
    }

    @PutMapping("/{id}")
    fun put(@PathVariable id: Int, @RequestBody sudoku: Sudoku) : ResponseEntity<Sudoku> {
        sudoku.id = id
        return ResponseEntity.ok(ss.save(sudoku))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) : ResponseEntity<Any> {
        return ResponseEntity.accepted().build()
    }
}