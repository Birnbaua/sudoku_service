package at.birnbaua.sudoku_service.jpa.entity.sudoku

import org.hibernate.annotations.NaturalId
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

/**
 * Entity for storing a difficulty level and all additional information
 * @since 1.0
 * @author Andreas Bachl
 */
@Entity
@Table(name = "`difficulty`")
@Suppress("unused")
open class Difficulty(

    @Id
    @Min(0)
    @Column(name = "`no`")
    open var no: Int? = null,

    @NaturalId
    @Max(32)
    @Column(name = "`name`", length = 32, nullable = false)
    open var name: String? = null,

    @Column(name = "`desc`", length = 255, nullable = true)
    open var desc: String? = null,

    /**
     * how many filled in cells a sudoku should have at least
     */
    @Size(min = 0, max = 81)
    @Column(name = "`min`", nullable = false)
    open var min: Int? = 40,

    /**
     * how many filled in cells a sudoku should have max.
     */
    @Size(min = 0, max = 81)
    @Column(name = "`max`", nullable = false)
    open var max: Int? = 45
)


