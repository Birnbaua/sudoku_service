package at.birnbaua.sudoku_service.jpa

interface SudokuGetInfo {
    val id: Int?
    val desc: String?
    val unsolved: String?
    val grouping: String?
    val difficulty: DifficultyInfo?

    interface DifficultyInfo {
        val no: Int?
        val desc: String?
        val name: String?
    }
}