package at.birnbaua.sudoku_service.jpa.solver

import at.birnbaua.sudoku_service.exception.NoValidSolutionException
import at.birnbaua.sudoku_service.jpa.validation.SudokuValidator
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * This class is there for solving incomplete sudokus automatically.
 * @since 1.0
 * @author Andreas Bachl
 */
@Service
class SudokuSolver {

    /**
     * @return The solved sudoku.
     * @since 1.0
     * @param id Id of the sudoku (important for caching)
     * @param sudoku The incomplete Sudoku which should be solved
     */
    @Cacheable("solved")
    fun solveNormal(id: Int, sudoku: String) : String {
        return Companion.solveNormal(sudoku)
    }

    companion object {

        private val empty = "0".toByte()
        private val validator = SudokuValidator()

        /**
         * @return true if there is one (or more) solutions for the given (incomplete) sudoku
         * @since 1.0
         * @param solve Sudoku which should be checked
         */
        fun isSolvable(solve: Array<ByteArray>) : Boolean {
            val clonedSudoku = Array(9) { ByteArray(9) }
            solve.forEachIndexed { index, bytes ->  bytes.forEachIndexed{ index1, byte -> clonedSudoku[index][index1] = byte }}
            return solve(clonedSudoku)
        }

        /**
         * @return true if there is one (or more) solutions for the given (incomplete) sudoku
         * @since 1.0
         * @param solve Sudoku which should be checked
         */
        fun solveNormal(solve: String) : String {
            SudokuValidator.validateUnfinishedStructure(solve)
            val arr = SudokuValidator.to2DArray(solve)
            return solveNormal(arr)
        }

        /**
         * Helper function for formatting output
         */
        private fun solveNormal(solve: Array<ByteArray>) : String {
            if(solve(solve)) {
                return solve.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","")
            } else {
                throw NoValidSolutionException("There is no valid solution for the given sudoku")
            }
        }

        /**
         * Backtracking method for solving a given incomplete sudoku
         * @return true if solving was successfully (or if sudoku was already finished)
         * @since 1.0
         * @param solve Sudoku which should be solved
         */
        private fun solve(solve: Array<ByteArray>) : Boolean {
            for (row in 0..8) {
                for (column in 0..8) {
                    if(solve[row][column] == empty) {
                        for (i in 1..9) {
                            solve[row][column] = i.toByte()
                            if(isCellValid(solve,row,column)) {
                                if(solve(solve)) {
                                    return true
                                }
                            }
                            solve[row][column] = empty
                        }
                        return false
                    }
                }
            }
            return true
        }

        /**
         * Checks if for a specific cell: Row, Column and Substructure constraint is fulfilled.
         * @return Boolean if valid
         * @since 1.0
         * @param sudoku 2D representation of sudoku
         * @param row Row no of cell. Starting with 0
         * @param row Column no of cell. Starting with 0
         */
        fun isCellValid(sudoku: Array<ByteArray>, row: Int, column: Int) : Boolean {
            return incompleteRowValid(sudoku, row) && incompleteColumnValid(sudoku, column) && incompleteSubsectionValid(sudoku,row/3,column/3)
        }

        fun incompleteSubsectionValid(sudoku: Array<ByteArray>, cellRow: Int, cellColumn: Int) : Boolean {
            return validator.validateIncompleteSubstructure(sudoku,cellRow,cellColumn)
        }

        fun incompleteRowValid(sudoku: Array<ByteArray>, row: Int) : Boolean {
            return validator.validateIncompleteRow(sudoku,row)
        }

        fun incompleteColumnValid(sudoku: Array<ByteArray>, column: Int) : Boolean {
            return validator.validateIncompleteColumn(sudoku,column)
        }

        fun printSudoku(sudoku: Array<ByteArray>) {
            sudoku.forEach { s ->
                s.forEach { c -> print("$c ") }
                println()
            }
        }

    }
}