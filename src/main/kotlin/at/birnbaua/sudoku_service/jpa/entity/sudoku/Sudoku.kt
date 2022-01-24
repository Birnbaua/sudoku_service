package at.birnbaua.sudoku_service.jpa.entity.sudoku

import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import at.birnbaua.sudoku_service.jpa.entity.Ownership
import org.slf4j.LoggerFactory
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Size

/**
 * Entity for storing a sudoku and all additional information with reference to other entities like [Difficulty]
 * @since 1.0
 * @author Andreas Bachl
 */
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

    /**
     * Not used but the idea is that we can use this for weird substructures in different sudoku types.
     */
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
    open var type: SudokuType? = null,

    @Lob
    @Column(name = "`preview`")
    open var preview: ByteArray? = null

) : Ownership() {

    companion object Logger {
        val log = LoggerFactory.getLogger(Sudoku::class.java)
    }

    @PrePersist
    private fun prePersist() {
        /*
        if(currentResult != null) {
            SudokuValidation.validateUnfinishedStructure(currentResult!!)
        }
         */
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now())
        this.createdAt = Timestamp.valueOf(LocalDateTime.now())
        checkConstraint()
    }

    @PreUpdate
    private fun preUpdate() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now())
        checkConstraint()
    }

    private fun checkConstraint() {
        if(unsolved?.matches("^[0-9]*\$".toRegex()) == false) {
            throw InvalidSudokuException("The unsolved Sudoku contains invalid characters. It may only contain digits from 0 to 9",
                log
            )
        }
    }
}

