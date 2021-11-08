package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.jpa.Sudoku
import at.birnbaua.sudoku_service.jpa.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.SudokuInfo
import at.birnbaua.sudoku_service.jpa.SudokuService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        return ResponseEntity.ok(ss.findByIdGetInfo(id))
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

    @ExceptionHandler
    fun handle(e: Exception) : ResponseEntity<Any> {
        log.error(e.stackTraceToString())
        return ResponseEntity.badRequest().build()
    }
}