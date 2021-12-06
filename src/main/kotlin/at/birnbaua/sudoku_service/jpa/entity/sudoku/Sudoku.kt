package at.birnbaua.sudoku_service.jpa.entity.sudoku

import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import org.slf4j.LoggerFactory
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

    @Enumerated(EnumType.STRING)
    @Column(name = "`type`")
    open var type: SudokuType? = null

) {

    companion object Logger {
        val log = LoggerFactory.getLogger(Sudoku::class.java)
    }

    @PrePersist
    @PreUpdate
    fun checkConstraint() {
        if(unsolved?.matches("^[0-9]*\$".toRegex()) == false) {
            throw InvalidSudokuException("The unsolved Sudoku contains invalid characters. It may only contain digits from 0 to 9",
                log
            )
        }
    }
}

