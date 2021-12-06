package at.birnbaua.sudoku_service.jpa.repository

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStatsId
import at.birnbaua.sudoku_service.jpa.projection.GameStatsInfo
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GameStatsRepository : JpaRepository<GameStats, GameStatsId> {
    fun findGameStatsByUserOrderByUpdatedAtDesc(user: User, pageable: Pageable) : Page<GameStatsInfo>
    fun findGameStatsByUserAndSudoku(user: User, sudoku: Sudoku) : GameStatsInfo
}