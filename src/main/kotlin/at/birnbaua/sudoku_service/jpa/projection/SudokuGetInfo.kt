package at.birnbaua.sudoku_service.jpa.projection

import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType

interface SudokuGetInfo {
    val id: Int?
    val desc: String?
    val type: SudokuType?
    val unsolved: String?
    val grouping: String?
    val difficulty: DifficultyInfo?

    interface DifficultyInfo {
        val no: Int?
        val desc: String?
        val name: String?
    }
}