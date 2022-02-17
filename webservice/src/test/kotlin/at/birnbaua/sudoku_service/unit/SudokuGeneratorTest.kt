package at.birnbaua.sudoku_service.unit

import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.validation.SudokuValidator
import at.birnbaua.sudoku_service.jpa.generator.SudokuGenerator
import at.birnbaua.sudoku_service.jpa.solver.SudokuSolver
import org.junit.jupiter.api.Test

class SudokuGeneratorTest {

    /**
     * As we already know that our solver is working fine, we can use it here to test our generated sudoku
     * At first with the first generator we didn't quite know why we mostly generated sudokus which are not solvable
     * After a long time of trial and error we noticed that we cannot just create a sudoku with random numbers but we must have a complete sudoku
     * and then take certain cells away.
     */
    @Test
    fun testGenerator() {
        val gen = SudokuGenerator()
        for(i in 0..50) {
            val sudoku = gen.genRandomSudoku(System.currentTimeMillis(), Difficulty())
            assert(SudokuSolver.isSolvable(SudokuValidator.to2DArray(sudoku)))
        }
    }
}