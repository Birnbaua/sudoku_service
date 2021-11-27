package at.birnbaua.sudoku_service.jpa.projection

import at.birnbaua.sudoku_service.jpa.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.sudoku.SudokuType
import java.sql.Time

interface GameStatsInfo {
    val duration: Time?
    val preview: ByteArray?
    val isFinished: Boolean?
    val sudoku: SudokuInfo?

    interface SudokuInfo {
        val id: Int?
        val difficulty: Difficulty?
        val type: SudokuType?
    }
}