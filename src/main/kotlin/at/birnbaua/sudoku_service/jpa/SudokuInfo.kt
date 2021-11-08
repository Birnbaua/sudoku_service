package at.birnbaua.sudoku_service.jpa

interface SudokuInfo {
    val id: Int?
    val desc: String?
    val difficulty: DifficultyInfo?

    interface DifficultyInfo {
        val no: Int?
        val name: String?
    }
}