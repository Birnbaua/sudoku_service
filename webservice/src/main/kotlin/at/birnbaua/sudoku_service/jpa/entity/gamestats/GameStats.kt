package at.birnbaua.sudoku_service.jpa.entity.gamestats

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.entity.AbstractEntity
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "`game_stats`")
@IdClass(GameStatsId::class)
open class GameStats(

    @Id
    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "`sudoku`", referencedColumnName = "`id`")
    open var sudoku: Sudoku? = null,

    @Id
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "`user`", referencedColumnName = "`username`")
    open var user: User? = null,

    @Column(name = "`duration`", nullable = false)
    open var duration: Time? = null,

    @NotNull
    @Column(name = "`current_result`")
    open var currentResult: String? = null,

    @Column(name = "`is_finished`")
    open var finished: Boolean? = false,

    @Lob
    @Column(name = "`preview`")
    open var preview: ByteArray? = null

) : AbstractEntity() {

    @PrePersist
    private fun prePersist() {
        /*
        if(currentResult != null) {
            SudokuValidation.validateUnfinishedStructure(currentResult!!)
        }
         */
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now())
        this.createdAt = Timestamp.valueOf(LocalDateTime.now())
    }

    @PreUpdate
    private fun preUpdate() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now())
    }
}