package at.birnbaua.sudoku_service.jpa.entity.gamestats

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.entity.AbstractEntity
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.validation.SudokuValidation
import java.sql.Time
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "`game_stats`")
@IdClass(GameStatsId::class)
open class GameStats(

    @Id
    @ManyToOne
    @JoinColumn(name = "`sudoku`", referencedColumnName = "`id`")
    open var sudoku: Sudoku? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`user`", referencedColumnName = "`username`")
    open var user: User? = null,

    @Column(name = "`duration`", nullable = false)
    open var duration: Time? = null,

    @NotNull
    @Column(name = "`current_result`")
    open var currentResult: String? = null,

    @Column(name = "`is_finished`")
    open var isFinished: Boolean? = false,

    @Lob
    @Column(name = "`preview`")
    open var preview: ByteArray? = null

) : AbstractEntity() {

    @PrePersist
    @PreUpdate
    private fun validate() {
        if(currentResult != null) {
            SudokuValidation.validateUnfinishedStructure(currentResult!!)
        }
    }
}