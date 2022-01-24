package at.birnbaua.sudoku_service.unit

import at.birnbaua.sudoku_service.jpa.validation.SudokuValidator
import at.birnbaua.sudoku_service.jpa.solver.SudokuSolver
import org.junit.jupiter.api.Test

class SudokuSolverTest {


    /**
     * What we noticed here is that for the sudoku "test2" our solver generates another but also correct solution!
     * That's why we didn't compare the strings here
     */
    @Test
    fun testNormalSolver() {
        val test2 = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        val sudoku = "009876412" +
                "728314905" +
                "641295738" +
                "062539801" +
                "385721649" +
                "197468253" +
                "256187394" +
                "913640087" +
                "874953126"
        val solved = "539876412" +
                "728314965" +
                "641295738" +
                "462539871" +
                "385721649" +
                "197468253" +
                "256187394" +
                "913642587" +
                "874953126"

        val validator = SudokuValidator()
        assert(validator.validateNormal(SudokuValidator.to2DArray(SudokuSolver.solveNormal(test2))))
        assert(SudokuSolver.solveNormal(sudoku) == solved)
    }
}