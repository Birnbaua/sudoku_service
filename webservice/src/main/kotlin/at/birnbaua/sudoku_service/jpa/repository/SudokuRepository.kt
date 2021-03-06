package at.birnbaua.sudoku_service.jpa.repository

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.projection.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface SudokuRepository : JpaRepository<Sudoku, Int> {
    @Query("SELECT s FROM Sudoku s ORDER BY s.id DESC")
    fun overview(pageable: Pageable): Page<SudokuInfo>

    @Query("SELECT s FROM Sudoku s WHERE s.difficulty.no=?1")
    fun overview(difficulty: Int, pageable: Pageable): Page<SudokuInfo>

    @Query("SELECT s FROM Sudoku s WHERE s.id=?1")
    fun findByIdGetInfo(id: Int) : Optional<SudokuGetInfo>

    fun findSudokusByDifficulty(difficulty: Difficulty, pageable: Pageable) : Page<SudokuInfo>

    fun existsSudokuByIdAndOwner(id: Int, owner: User) : Boolean

    fun findAllByType(type: SudokuType, pageable: Pageable) : Page<Sudoku>

    @Query("SELECT s FROM Sudoku s WHERE s.type=?1")
    fun overview(type: SudokuType, pageable: Pageable) : Page<SudokuInfo>

    @Query("SELECT s FROM Sudoku s WHERE s.type=?2 AND s.difficulty.no=?1")
    fun overview(difficulty: Int, type: SudokuType, pageable: Pageable) : Page<SudokuInfo>
}