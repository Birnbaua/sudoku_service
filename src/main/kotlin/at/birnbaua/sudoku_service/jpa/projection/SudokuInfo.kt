package at.birnbaua.sudoku_service.jpa.projection

import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType

interface SudokuInfo {
    val id: Int?
    val desc: String?
    val type: SudokuType?
    val preview: ByteArray?
    val difficulty: DifficultyInfo?

    interface DifficultyInfo {
        val no: Int?
        val name: String?
    }
}