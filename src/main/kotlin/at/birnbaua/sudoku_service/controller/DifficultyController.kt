package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.exception.EntityNotFoundException
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.jpaservice.DifficultyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * This controller is responsible for all saving and getting the [Difficulty] levels of sudokus.
 * The [RequestMapping] is /difficulty
 * @since 1.0
 * @author Andreas Bachl
 */
@CrossOrigin
@RestController
@RequestMapping("/difficulty")
class DifficultyController {

    @Autowired
    private lateinit var ds: DifficultyService

    /**
     * @return All difficulty levels
     * @since 1.0
     */
    @GetMapping
    fun getAll() : ResponseEntity<Iterable<Difficulty>> {
        return ResponseEntity.ok(ds.findAll())
    }

    /**
     * @return The difficulty level with requested number
     * @since 1.0
     * @param no Number of the requested difficulty
     */
    @GetMapping("/{no}")
    fun get(@PathVariable no: Int) : ResponseEntity<Difficulty> {
        return ResponseEntity.ok(ds.findById(no).orElseThrow { throw EntityNotFoundException("No difficulty existing with id: $no") })
    }

    /**
     * @return The saved difficulty level
     * @since 1.0
     */
    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @PostMapping
    fun post(@RequestBody difficulty: Difficulty) : ResponseEntity<Difficulty> {
        return ResponseEntity.status(HttpStatus.CREATED).body(ds.save(difficulty))
    }

    /**
     * @return The modified difficulty level
     * @since 1.0
     */
    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @PutMapping("/{no}")
    fun put(@PathVariable no: Int, @RequestBody difficulty: Difficulty) : ResponseEntity<Difficulty> {
        difficulty.no = no
        return ResponseEntity.ok(ds.save(difficulty))
    }

    /**
     * Deletes all difficulty levels.
     * @return nothing.
     * @since 1.0
     */
    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @DeleteMapping
    fun deleteAll() : ResponseEntity<*> {
        return ResponseEntity.ok(ds.deleteAll())
    }

    /**
     * Deletes the requested difficulty level.
     * @return nothing.
     * @since 1.0
     * @param no The number of the difficulty level to be deleted.
     */
    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{no}")
    fun delete(@PathVariable no: Int) : ResponseEntity<*> {
        return ResponseEntity.ok(ds.deleteById(no))
    }
}