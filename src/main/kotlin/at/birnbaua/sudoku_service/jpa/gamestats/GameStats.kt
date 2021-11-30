package at.birnbaua.sudoku_service.jpa.gamestats

import at.birnbaua.sudoku_service.jpa.AbstractEntity
import at.birnbaua.sudoku_service.jpa.Ownership
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo
import at.birnbaua.sudoku_service.jpa.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.user.User
import at.birnbaua.sudoku_service.jpaservice.JpaService
import at.birnbaua.sudoku_service.jpa.sudoku.validation.SudokuValidation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.io.Serializable
import java.sql.Time
import javax.persistence.*

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

data class GameStatsId(
    var sudoku: Int? = null,
    var user: String? = null
) : Serializable

@Repository
interface GameStatsRepository : JpaRepository<GameStats,GameStatsId> {
    fun findGameStatsByUserOrderByUpdatedAtDesc(user: User, pageable: Pageable) : Page<GameStatsInfo>
    fun findGameStatsByUserAndSudoku(user: User, sudoku: Sudoku) : GameStatsInfo
}

@Service
class GameStatsService(@Autowired val repo: GameStatsRepository) : JpaService<GameStats,GameStatsId>(repo) {

    fun saveInfo(stats: GameStats) : GameStatsInfo {
        val saved = repo.save(stats)
        return repo.findGameStatsByUserAndSudoku(saved.user!!,saved.sudoku!!)
    }

    fun findGameStatsByUser(username: String, page: Int, size: Int) : Page<GameStatsInfo> {
        return repo.findGameStatsByUserOrderByUpdatedAtDesc(User(username), PageRequest.of(page,size))
    }
}