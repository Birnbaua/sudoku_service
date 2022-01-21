package at.birnbaua.sudoku_service.jpa.solver

data class Hint(
    val row: Byte,
    val column: Byte,
    val value: Byte
)