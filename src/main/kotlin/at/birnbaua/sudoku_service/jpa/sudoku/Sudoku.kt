package at.birnbaua.sudoku_service.jpa.sudoku

import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import at.birnbaua.sudoku_service.jpaservice.JpaService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "`sudoku`")
open class Sudoku(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    open var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "`difficulty`")
    open var difficulty: Difficulty? = Difficulty(1),

    @Size(min = 81, max = 81)
    @Column(name = "`solved_sudoku`", length = 81)
    open var solved: String? = null,

    @Size(min = 81, max = 81)
    @Column(name = "`unsolved_sudoku`")
    open var unsolved: String? = null,

    @Column(name = "`desc`")
    open var desc: String? = null,

    @Size(min = 81, max = 81)
    @Column(name = "`grouping`", length = 81, nullable = false)
    open var grouping: String? = "111222333" +
            "111222333" +
            "111222333" +
            "444555666" +
            "444555666" +
            "444555666" +
            "777888999" +
            "777888999" +
            "777888999",

    @ManyToOne
    @JoinColumn(name = "`type`", referencedColumnName = "`name`")
    open var type: SudokuType? = null

) {

    companion object Logger {
        val log = LoggerFactory.getLogger(Sudoku::class.java)
    }

    @PrePersist
    @PreUpdate
    fun checkConstraint() {
        if(solved?.matches("^[1-9]*\$".toRegex()) == false) {
            throw InvalidSudokuException("The solved Sudoku contains invalid characters. It may only contain digits from 1 to 9",
                log
            )
        }
        if(unsolved?.matches("^[0-9]*\$".toRegex()) == false) {
            throw InvalidSudokuException("The unsolved Sudoku contains invalid characters. It may only contain digits from 0 to 9",
                log
            )
        }
    }
}

@Repository
interface SudokuRepository : JpaRepository<Sudoku,Int> {
    @Query("SELECT s FROM Sudoku s")
    fun overview(pageable: Pageable): Page<SudokuInfo>

    @Query("SELECT s FROM Sudoku s WHERE s.id=?1")
    fun findByIdGetInfo(id: Int) : SudokuGetInfo?

    fun findSudokusByDifficulty(difficulty: Difficulty, pageable: Pageable) : Page<SudokuInfo>
}

@Service
class SudokuService @Autowired constructor(val rep: SudokuRepository) : JpaService<Sudoku, Int>(rep) {

    fun overview(page: Int = 0, size: Int = 30) : Page<SudokuInfo> {
        return rep.overview(PageRequest.of(page,size))
    }

    fun findByIdGetInfo(id: Int) : SudokuGetInfo? {
        return rep.findByIdGetInfo(id)
    }

    fun findSudokusByDifficulty(difficulty: Int, pageable: Pageable = PageRequest.of(0,30)) : Page<SudokuInfo> {
        return rep.findSudokusByDifficulty(Difficulty(difficulty),pageable)
    }
}