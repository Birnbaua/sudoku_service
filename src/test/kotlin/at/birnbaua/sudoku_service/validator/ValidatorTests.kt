package at.birnbaua.sudoku_service.validator

import at.birnbaua.sudoku_service.jpa.entity.sudoku.validation.SudokuValidation
import at.birnbaua.sudoku_service.jpa.solver.Solver
import org.junit.jupiter.api.Test

class ValidatorTests {

    @Test
    fun testNormalValidator() {
        val validator = SudokuValidation()
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
            assert(validator.validRow(SudokuValidation.to2DArray(sudoku),i))
        }
        for(i in 0..8) {
            assert(validator.validColumn(SudokuValidation.to2DArray(sudoku),i))
        }
        assert(validator.validateNormal(SudokuValidation.to2DArray(sudoku)))
    }

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
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),0,0))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),0,1))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),0,2))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),1,0))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),1,1))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),1,2))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),2,0))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),2,1))
        assert(Solver.incompleteSubsectionValid(SudokuValidation.to2DArray(test2),2,2))
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
        assert(Solver.incompleteRowValid(SudokuValidation.to2DArray(test2),0))
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
        assert(Solver.incompleteColumnValid(SudokuValidation.to2DArray(test2),0))
    }


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
        println(Solver.solveNormal(test2))

        println(Solver.printSudoku(SudokuValidation.to2DArray(Solver.solveNormal(test2))))

        val validator = SudokuValidation()
        assert(validator.validateNormal(SudokuValidation.to2DArray(Solver.solveNormal(test2))))
        println(solved)
        assert(Solver.solveNormal(sudoku) == solved)
    }
}