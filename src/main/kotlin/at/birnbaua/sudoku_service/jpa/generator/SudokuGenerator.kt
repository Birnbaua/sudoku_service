package at.birnbaua.sudoku_service.jpa.generator

import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.entity.sudoku.validation.SudokuValidation
import at.birnbaua.sudoku_service.jpa.solver.Solver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class SudokuGenerator {

    @Autowired
    private lateinit var solver: Solver

    private val normalSize = 3
    private val numbers = listOf<Byte>(1,2,3,4,5,6,7,8,9)

    fun genRandomSudoku(seed: Long, difficulty: Difficulty) : String {
        val sudoku = Array(9) { ByteArray(9) }
        gen(sudoku)
        val random = Random(seed)
        val solved = sudoku.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","").toCharArray()
        for(i in 0..40) {
            solved[random.nextInt(81)] = '0'
        }
        return solved.concatToString()
    }

    fun genRandomSudokuWithDiagonalApproach(seed: Long, difficulty: Difficulty) : String {
        var independentSubSections = listOf(Pair(0,0),Pair(0,2),Pair(1,1),Pair(2,0),Pair(2,2))
        return ""
    }

    private fun gen(solve: Array<ByteArray>) : Boolean {
        for (row in 0..8) {
            for (column in 0..8) {
                if(solve[row][column] == 0.toByte()) {
                    val numbers = mutableListOf<Byte>(1,2,3,4,5,6,7,8,9).shuffled()
                    for (i in numbers) {
                        solve[row][column] = i
                        if(Solver.isCellValid(solve, row, column)) {
                            if(gen(solve)) {
                                return true
                            }
                        }
                        solve[row][column] = 0.toByte()
                    }
                    return false
                }
            }
        }
        return true
    }


    /*
    not working
     */
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
            } while (!Solver.isCellValid(sudoku,i/9,i%9))
        }
        return sudoku.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","")
    }

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
            } while (!Solver.isSolvable(sudoku))
            emptyCells.removeAt(index)
            number = number.inc()
        } while (number < normalSize)
        return sudoku.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","")
    }

    fun genRandomSudoku(sudoku: String, difficulty: Difficulty) : Sudoku {
        return Sudoku(null,difficulty,null,sudoku,"",SudokuType.NORMAL)
    }

}