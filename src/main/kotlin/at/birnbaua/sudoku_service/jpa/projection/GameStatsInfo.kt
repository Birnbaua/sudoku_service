package at.birnbaua.sudoku_service.jpa.projection

import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import java.sql.Time
import java.sql.Timestamp

interface GameStatsInfo {
    val createdAt: Timestamp?
    val currentResult: String?
    val preview: ByteArray?
    val updatedAt: Timestamp?
    val finished: Boolean?
    val sudoku: SudokuInfo?
    val user: UserInfo?
    val duration: Time?

    interface SudokuInfo {
        val id: Int?
        val difficulty: Difficulty?
        val type: SudokuType?
    }

    interface UserInfo {
        val username: String?
        val firstName: String?
        val lastName: String?
    }
}