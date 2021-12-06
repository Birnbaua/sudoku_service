package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.exception.EntityNotFoundException
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.jpaservice.DifficultyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/difficulty")
class DifficultyController {

    @Autowired
    lateinit var ds: DifficultyService

    @GetMapping
    fun getAll() : ResponseEntity<Iterable<Difficulty>> {
        return ResponseEntity.ok(ds.findAll())
    }

    @GetMapping("/{no}")
    fun get(@PathVariable no: Int) : ResponseEntity<Difficulty> {
        return ResponseEntity.ok(ds.findById(no).orElseThrow { throw EntityNotFoundException("No difficulty existing with id: $no") })
    }

    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @PostMapping
    fun post(@RequestBody difficulty: Difficulty) : ResponseEntity<Difficulty> {
        return ResponseEntity.status(HttpStatus.CREATED).body(ds.save(difficulty))
    }

    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @PutMapping("/{no}")
    fun put(@PathVariable no: Int, @RequestBody difficulty: Difficulty) : ResponseEntity<Difficulty> {
        difficulty.no = no
        return ResponseEntity.ok(ds.save(difficulty))
    }

    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @DeleteMapping
    fun deleteAll() {
        ds.deleteAll()
    }

    @PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{no}")
    fun delete(@PathVariable no: Int) {
        ds.deleteById(no)
    }
}