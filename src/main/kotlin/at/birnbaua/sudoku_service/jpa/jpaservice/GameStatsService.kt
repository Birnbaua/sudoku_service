package at.birnbaua.sudoku_service.jpa.jpaservice

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStatsId
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo2
import at.birnbaua.sudoku_service.jpa.repository.GameStatsRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GameStatsService(@Autowired val repo: GameStatsRepository) : JpaService<GameStats, GameStatsId>(repo) {

    private val log = LoggerFactory.getLogger(GameStatsService::class.java)

    @Transactional
    fun deleteById(username: String, sudokuID: Int) {
        repo.deleteByUserAndSudoku(User(username), Sudoku(sudokuID))
    }

    fun saveInfo(stats: GameStats) : GameStatsInfo {
        val saved = repo.save(stats)
        return repo.findGameStatsByUserAndSudoku(saved.user!!,saved.sudoku!!)
    }

    fun findGameStatsByUser(username: String, page: Int, size: Int) : Page<GameStatsInfo> {
        log.info("findByUser: $username, $page, $size")
        return repo.findGameStatsByUserOrderByUpdatedAtDesc(User(username), PageRequest.of(page,size))
    }
}