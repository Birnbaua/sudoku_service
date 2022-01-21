package at.birnbaua.sudoku_service.validator

import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.validation.SudokuValidation
import at.birnbaua.sudoku_service.jpa.generator.SudokuGenerator
import at.birnbaua.sudoku_service.jpa.solver.Solver
import org.junit.jupiter.api.Test

class SudokuGeneratorTest {

    @Test
    fun testGenerator() {
        val gen = SudokuGenerator()
        for(i in 0..500) {
            val sudoku = gen.genRandomSudoku(System.currentTimeMillis(), Difficulty())
            Solver.printSudoku(SudokuValidation.to2DArray(sudoku))
            print(Solver.solveNormal(sudoku))
        }
    }
}