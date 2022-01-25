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

    @Test
    fun testDiagonalValidator() {
        val validator = SudokuValidator()
        val diagonalSudoku = "639841275" +
                "724953168" +
                "185726394" +
                "256137489" +
                "491582637" +
                "873469521" +
                "542398716" +
                "318675942" +
                "967214853"

        val normalSudoku = "539876412" +
                "728314965" +
                "641295738" +
                "462539871" +
                "385721649" +
                "197468253" +
                "256187394" +
                "913642587" +
                "874953126"
        assert(validator.validateDiagonal(SudokuValidator.to2DArray(diagonalSudoku)))
        assert(!validator.validateDiagonal(SudokuValidator.to2DArray(normalSudoku)))
    }

    @Test
    fun testSamuraiValidator() {
        val validator = SudokuValidator()
        val sudoku1 = "924538617" +
                "358617924" +
                "716492853" +
                "635789241" +
                "297146538" +
                "481325796" +
                "873254169" +
                "162973485" +
                "549861372"
        val sudoku2 = "731246895" +
                "259813467" +
                "684975312" +
                "398524176" +
                "126739584" +
                "547168239" +
                "472681953" +
                "913457628" +
                "865392741"
        val sudoku3 = "169538472" +
                "485627913" +
                "372941865" +
                "654719328" +
                "218364759" +
                "793852641" +
                "547293186" +
                "921486537" +
                "836175294"
        val sudoku4 = "189326547" +
                "346587921" +
                "527914836" +
                "238659174" +
                "765841293" +
                "491273658" +
                "612435789" +
                "873192465" +
                "954768312"
        val sudoku5 = "186495732" +
                "537281694" +
                "294763581" +
                "453819267" +
                "628537419" +
                "719642853" +
                "861374925" +
                "342956178" +
                "975128346"

        /*
        check if all sudokus are valid first for better debugging
         */
        assert(validator.validateNormal(SudokuValidator.to2DArray(sudoku1)))
        assert(validator.validateNormal(SudokuValidator.to2DArray(sudoku2)))
        assert(validator.validateNormal(SudokuValidator.to2DArray(sudoku3)))
        assert(validator.validateNormal(SudokuValidator.to2DArray(sudoku4)))
        assert(validator.validateNormal(SudokuValidator.to2DArray(sudoku5)))

        val sudokus = mutableListOf(
            SudokuValidator.to2DArray(sudoku1),
            SudokuValidator.to2DArray(sudoku2),
            SudokuValidator.to2DArray(sudoku3),
            SudokuValidator.to2DArray(sudoku4),
            SudokuValidator.to2DArray(sudoku5))

        assert(validator.validateSamurai(sudokus))

    }


}