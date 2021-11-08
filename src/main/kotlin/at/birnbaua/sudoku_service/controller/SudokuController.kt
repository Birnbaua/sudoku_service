package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.Sudoku
import at.birnbaua.sudoku_service.jpa.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.SudokuInfo
import at.birnbaua.sudoku_service.jpa.SudokuService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/sudoku")
class SudokuController {

    val log: Logger = LoggerFactory.getLogger(SudokuController::class.java)

    @Autowired
    lateinit var ss: SudokuService

    @GetMapping
    fun all() : ResponseEntity<Set<SudokuInfo>> {
        return ResponseEntity.ok(ss.overview())
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) : ResponseEntity<SudokuGetInfo> {
        val sudoku = ss.findByIdGetInfo(id) ?: throw SudokuNotExistingException(id,log)
        return ResponseEntity.ok(sudoku)
    }

    @GetMapping("/{id}/validate")
    fun validate(@PathVariable id: Int, @RequestBody(required = false) solved: String?, @RequestParam(required = false, name = "solved") solvedParam: String?) : ResponseEntity<Boolean> {
        return if(solvedParam != null) {
            ResponseEntity.ok(ss.findById(id).orElseThrow{SudokuNotExistingException(id,log)}.solved.equals(solvedParam))
        } else if(solved != null) {
            ResponseEntity.ok(ss.findById(id).orElseThrow{SudokuNotExistingException(id,log)}.solved.equals(solved))
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping
    fun post(@RequestBody sudoku: Sudoku) : ResponseEntity<Sudoku> {
        return ResponseEntity.ok(ss.save(sudoku))
    }

    @PutMapping("/{id}")
    fun put(@PathVariable id: Int, @RequestBody sudoku: Sudoku) : ResponseEntity<Sudoku> {
        return ResponseEntity.ok(ss.save(sudoku))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) : ResponseEntity<Any> {
        return ResponseEntity.accepted().build()
    }
}