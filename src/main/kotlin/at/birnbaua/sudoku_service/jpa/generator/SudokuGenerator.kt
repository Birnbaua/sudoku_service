package at.birnbaua.sudoku_service.jpa.generator

import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.solver.SudokuSolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random

/**
 * This class is there for generating sudokus automatically.
 * @since 1.0
 * @author Andreas Bachl
 */
@Component
class SudokuGenerator {

    private val normalSize = 5
    private val numbers = listOf<Byte>(1,2,3,4,5,6,7,8,9)

    /**
     * Generate a classic Sudoku with given difficulty constrains
     * @return A generated sudoku
     * @since 1.0
     * @param seed Seed for random numbers generator
     * @param difficulty Needed for constraints, how many starting numbers should be present
     */
    fun genRandomSudoku(seed: Long, difficulty: Difficulty) : String {
        val sudoku = Array(9) { ByteArray(9) }
        gen(sudoku)
        val random = Random(seed)
        val solved = sudoku.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","").toCharArray()
        val reduce = difficulty.max!! - random.nextInt(difficulty.max!! - difficulty.min!!)
        for(i in 0..reduce) {
            solved[random.nextInt(81)] = '0'
        }
        return solved.concatToString()
    }

    /**
     * Helper method for filling sudoku with backtracking approach like in the [SudokuSolver]
     * @return A generated sudoku
     * @since 1.0
     * @param sudoku The 2D array for filling in the sudoku
     */
    private fun gen(sudoku: Array<ByteArray>) : Boolean {
        for (row in 0..8) {
            for (column in 0..8) {
                if(sudoku[row][column] == 0.toByte()) {
                    val numbers = mutableListOf<Byte>(1,2,3,4,5,6,7,8,9).shuffled()
                    for (i in numbers) {
                        sudoku[row][column] = i
                        if(SudokuSolver.isCellValid(sudoku, row, column)) {
                            if(gen(sudoku)) {
                                return true
                            }
                        }
                        sudoku[row][column] = 0.toByte()
                    }
                    return false
                }
            }
        }
        return true
    }

    /**
     * Second try of sudoku generator
     */
    @Deprecated("generates invalid sudokus")
    fun genRandomSudokuString(seed: Long, difficulty: Difficulty) : String {
        val sudoku = Array(9) { ByteArray(9) }
        val random = Random(seed)
        val possibleNumbers = mutableListOf<Byte>()
        for(i in 0..80) {
            possibleNumbers.clear()
            possibleNumbers.addAll(numbers)
            println(i)
            do {
                if(possibleNumbers.isEmpty()) {
                    sudoku[i/9][i%9] = 0
                    break
                }
                sudoku[i/9][i%9] = possibleNumbers.removeAt(random.nextInt(possibleNumbers.size))
            } while (!SudokuSolver.isCellValid(sudoku,i/9,i%9))
        }
        return sudoku.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","")
    }

    /**
     * First try of generating sudoku
     */
    @Deprecated("Runs extremely long")
    fun genRandomSudokuStringExhaustive(seed: Long, difficulty: Difficulty) : String {
        val random = Random(seed)
        val sudoku = Array(9) { ByteArray(9) }
        val emptyCells = mutableListOf<Byte>()
        for(i in 0..80) emptyCells.add(i.toByte())
        val possibleNumbers = mutableListOf<Byte>()
        var row: Int
        var column: Int
        var number = 0
        var attempts = 0

        do {
            possibleNumbers.clear()
            possibleNumbers.addAll(numbers)
            val index = random.nextInt(emptyCells.size)
            row = emptyCells[index]/9
            column = emptyCells[index]%9
            do {
                sudoku[row][column] = possibleNumbers.removeAt(random.nextInt(possibleNumbers.size))
            } while (!SudokuSolver.isSolvable(sudoku))
            emptyCells.removeAt(index)
            number = number.inc()
        } while (number < normalSize)
        return sudoku.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","")
    }

}