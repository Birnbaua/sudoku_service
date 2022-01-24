package at.birnbaua.sudoku_service.unit

import at.birnbaua.sudoku_service.jpa.validation.SudokuValidator
import at.birnbaua.sudoku_service.jpa.solver.SudokuSolver
import org.junit.jupiter.api.Test

class ValidatorTests {

    @Test
    fun testIncompleteSubsection() {
        val test2 = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),0,0))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),0,1))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),0,2))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),1,0))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),1,1))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),1,2))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),2,0))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),2,1))
        assert(SudokuSolver.incompleteSubsectionValid(SudokuValidator.to2DArray(test2),2,2))
    }

    @Test
    fun testIncompleteRow() {
        val test2 = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        assert(SudokuSolver.incompleteRowValid(SudokuValidator.to2DArray(test2),0))
    }

    @Test
    fun testIncompleteColumn() {
        val test2 = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        assert(SudokuSolver.incompleteColumnValid(SudokuValidator.to2DArray(test2),0))
    }

    @Test
    fun testIncompleteValidator() {

    }

    @Test
    fun testNormalValidator() {
        val validator = SudokuValidator()
        val sudoku = "539876412" +
                "728314965" +
                "641295738" +
                "462539871" +
                "385721649" +
                "197468253" +
                "256187394" +
                "913642587" +
                "874953126"
        for(i in 0..8) {
            assert(validator.validateRow(SudokuValidator.to2DArray(sudoku),i))
        }
        for(i in 0..8) {
            assert(validator.validateColumn(SudokuValidator.to2DArray(sudoku),i))
        }
        assert(validator.validateNormal(SudokuValidator.to2DArray(sudoku)))
    }


}