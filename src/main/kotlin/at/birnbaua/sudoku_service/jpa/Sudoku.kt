package at.birnbaua.sudoku_service.jpa

import at.birnbaua.sudoku_service.controller.SudokuController
import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import at.birnbaua.sudoku_service.jpaservice.JpaService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "`sudoku`")
class Sudoku(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "`difficulty`")
    var difficulty: Difficulty? = Difficulty(0),

    @Size(min = 81, max = 81)
    @Column(name = "`solved_sudoku`", length = 81)
    var solved: String? = null,

    @Size(min = 81, max = 81)
    @Column(name = "`unsolved_sudoku`")
    var unsolved: String? = null,

    @Column(name = "`desc`")
    var desc: String? = null

) {

    companion object Logger {
        val log = LoggerFactory.getLogger(Sudoku::class.java)
    }

    @PrePersist
    @PreUpdate
    fun checkConstraint() {
        if(solved?.matches("^[1-9]*\$".toRegex()) == false) {
            throw InvalidSudokuException("The solved Sudoku contains invalid characters. It may only contain digits from 1 to 9",log)
        }
        if(unsolved?.matches("^[0-9]*\$".toRegex()) == false) {
            throw InvalidSudokuException("The unsolved Sudoku contains invalid characters. It may only contain digits from 0 to 9",log)
        }
    }
}

@Repository
interface SudokuRepository : JpaRepository<Sudoku,Int> {
    @Query("SELECT s FROM Sudoku s")
    fun overview(): Set<SudokuInfo>

    @Query("SELECT s FROM Sudoku s WHERE s.id=?1")
    fun findByIdGetInfo(id: Int) : SudokuGetInfo?
}

@Service
class SudokuService @Autowired constructor(val rep: SudokuRepository) : JpaService<Sudoku, Int>(rep) {

    fun overview() : Set<SudokuInfo> {
        return rep.overview()
    }

    fun findByIdGetInfo(id: Int) : SudokuGetInfo? {
        return rep.findByIdGetInfo(id)
    }
}