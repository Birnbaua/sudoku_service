package at.birnbaua.sudoku_service.jpa.entity.sudoku

/**
 * Enum for specifying the type of a sudoku. 'CUSTOM' is for sudokus which type are not implemented by the web service!
 * @since 1.0
 * @author Andreas Bachl
 */
enum class SudokuType{
    NORMAL,DIAGONAL,SAMURAI,CUSTOM
}