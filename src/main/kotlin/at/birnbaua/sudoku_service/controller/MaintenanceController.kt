package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.generator.SudokuGenerator
import at.birnbaua.sudoku_service.jpa.jpaservice.DifficultyService
import at.birnbaua.sudoku_service.jpa.jpaservice.GameStatsService
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.random.Random

@RestController
@RequestMapping("/maintenance")
class MaintenanceController {

    @Autowired
    lateinit var gss: GameStatsService

    @Autowired
    private lateinit var ss: SudokuService

    @Autowired
    private lateinit var sg: SudokuGenerator

    @Autowired
    private lateinit var ds: DifficultyService

    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @DeleteMapping
    fun deleteAllGameStats() : ResponseEntity<*> {
        return ResponseEntity.ok(gss.deleteAll())
    }

    @GetMapping("/sudoku/generate")
    fun generate(@RequestParam(required = false, defaultValue = "5") amount: Int?, @RequestParam(required = false) difficulty: Int? = null) : ResponseEntity<MutableList<Sudoku>> {
        val sudokus = mutableListOf<Sudoku>()
        val difficulties = ds.findAll()
        val random = Random(System.currentTimeMillis())
        for(i in 1..amount!!) {
            val dif: Difficulty = if(difficulty==null) {
                difficulties[random.nextInt(difficulties.size-1)]
            } else {
                Difficulty(difficulty)
            }
            val sudoku = Sudoku(
                null,
                dif,
                sg.genRandomSudoku(System.currentTimeMillis(), dif),
                UUID.randomUUID().toString(),
                "1233413241234",
                SudokuType.NORMAL)
            sudoku.owner = User("admin")
            sudokus.add(sudoku)
        }
        return ResponseEntity.ok(ss.saveAllAndFlush(sudokus))
    }

}