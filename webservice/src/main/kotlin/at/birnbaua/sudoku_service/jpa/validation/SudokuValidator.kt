package at.birnbaua.sudoku_service.jpa.validation

import org.slf4j.Logger
import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * This class is there for validating complete and incomplete sudokus.
 * @since 1.0
 * @author Andreas Bachl
 */
@Service
class SudokuValidator {

    @Autowired
    lateinit var ss: SudokuService
    private val log: Logger = LoggerFactory.getLogger(SudokuValidator::class.java)

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SudokuValidator::class.java)

        /**
         * @return If sudoku contains only numbers [0-9]
         * @since 1.0
         * @param unsolved The unsolved sudoku
         * @throws InvalidSudokuException
         */
        fun validateUnfinishedStructure(unsolved: String) {
            if(unsolved.matches("^[0-9]*\$".toRegex()).not()) {
                throw InvalidSudokuException("The unsolved Sudoku contains invalid characters. It may only contain digits from 0 to 9",
                    log
                )
            }
        }

        /**
         * @return If sudoku contains only numbers [1-9]
         * @since 1.0
         * @param solved The solved sudoku
         * @throws InvalidSudokuException
         */
        fun validateFinishedStructure(solved : String) : Boolean {
            if(solved.matches("^[1-9]*\$".toRegex()).not()) {
                throw InvalidSudokuException("The solved Sudoku contains invalid characters. It may only contain digits from 1 to 9",
                    log
                )
            }
            return true
        }

        /**
         * Transforms a sudoku string into a 2d [ByteArray]
         * @return A 2D representation of the given sudoku string
         * @since 1.0
         * @param sudoku Sudoku which should be transformed
         */
        fun to2DArray(sudoku: String) : Array<ByteArray> {
            val arr = Array(9) { ByteArray(9) }
            for(c in sudoku.toCharArray()?.withIndex()!!) {
                arr[c.index/9][c.index%9] = c.value.toString().toByte()
            }
            return arr
        }
    }

    /**
     * This is the general validation function. It works for all types of sudokus.
     * @return A boolean if the given sudoku is valid.
     * @since 1.0
     * @param id ID of the sudoku which should be checked.
     * @param check Sudoku which should be checked
     * @param type The [SudokuType] of the sudoku (important for checking different constraints!)
     */
    fun validate(id: Int, check: String, type: SudokuType = SudokuType.NORMAL) : Boolean {
        log.debug("Validate: $type")
        //check other constraints
        return when(type) {
            SudokuType.NORMAL -> validateNormal(to2DArray(check))
            SudokuType.DIAGONAL -> validateDiagonal(to2DArray(check))
            SudokuType.SAMURAI -> validateSamurai(check.split(";").map { x -> to2DArray(x) })
            else -> false
        }
    }

    /**
     * Checks if the given sudoku is in line with the stored one.
     * @return A boolean if the given sudoku is a sudoku of the stored one.
     * @since 1.0
     * @param id ID of the sudoku which should be compared
     * @param solved Sudoku which should be checked
     */
    fun checkIfRightSudoku(id: Int, solved: Array<ByteArray>) : Boolean {
        val original = to2DArray(ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }.unsolved!!)
        //check if same sudoku
        for(o1 in original.withIndex()) {
            for(o2 in o1.value.withIndex()) {
                if(o2.value.equals(0).not() && o1.value.equals(solved[o1.index][o2.index]).not()) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * This checks a complete sudoku. All cells must contain a number of the valid range [1,9].
     * @return False for incomplete or invalid classic sudoku.
     */
    fun validateNormal(sudoku: Array<ByteArray>) : Boolean {
        for(i in 0..8) {
            if(!validateRow(sudoku,i)) {
                return false
            }
            if(!validateColumn(sudoku,i)) {
                return false
            }
        }
        for (i in 0..2) {
            if(!validateSubstructure(sudoku,i,i)) {
                return false
            }
        }
        return true
    }

    /**
     * This checks if the sudoku has 81 numbers and is filled with valid numbers only [0,9].
     * @return True for sudokus of incomplete but valid sudokus and for an empty sudoku (only 0s) it returns also true.
     */
    fun validateIncompleteNormal(sudoku: String) : Boolean {
        val arr = to2DArray(sudoku)
        for (i in 0..8) {
            if(!(validateIncompleteRow(arr,i) || validateIncompleteColumn(arr,i) || validateIncompleteSubstructure(arr,i/3,i%3))) {
                return false
            }
        }
        return true
    }

    fun validateRow(sudoku: Array<ByteArray>, row: Int) : Boolean {
        return sudoku[row].distinct().size == 9 && sudoku[row].contains(0).not()
    }

    fun validateColumn(sudoku: Array<ByteArray>, column: Int) : Boolean {
        return sudoku.map { x -> x[column] }.distinct().size == 9 && sudoku[column].contains(0).not()
    }

    fun validateSubstructure(sudoku: Array<ByteArray>, cellRow: Int, cellColumn: Int) : Boolean {
        val str = sudoku.filterIndexed{ index, x -> index/3 == cellRow}.map { x ->
            x.copyOfRange(cellColumn*3,cellColumn*3+3)
        }.flatMap { x -> x.asList()
        }.toByteArray().distinct()
        return str.size == 9  && str.contains(0).not()
    }

    fun validateIncompleteSubstructure(sudoku: Array<ByteArray>, subsectionRow: Int, subsectionColumn: Int) : Boolean {
        var emptyCells = 0
        val arr = sudoku.filterIndexed{ index, x -> index/3 == subsectionRow}.map { x ->
            x.copyOfRange(subsectionColumn*3,subsectionColumn*3+3)
        }.flatMap { x -> x.asList()
        }.toByteArray()
        arr.forEach {x -> if( x == 0.toByte()) {emptyCells = emptyCells.inc()}}
        if(emptyCells != 0) {
            return arr.distinct().size == 9-emptyCells+1
        }
        return arr.distinct().size == 9
    }

    fun validateIncompleteRow(sudoku: Array<ByteArray>, row: Int) : Boolean {
        val emptyCells = sudoku[row].filter { x -> x == 0.toByte() }.size
        if(emptyCells != 0) {
            return sudoku[row].distinct().size == 9-emptyCells+1
        }
        return sudoku[row].distinct().size == 9
    }

    fun validateIncompleteColumn(sudoku: Array<ByteArray>, column: Int) : Boolean {
        val emptyCells = sudoku.map { x -> x[column] }.filter { x -> x == 0.toByte() }.size
        if(emptyCells != 0) {
            return sudoku.map { x -> x[column] }.distinct().size == 9-emptyCells+1
        }
        return sudoku.map { x -> x[column] }.distinct().size == 9
    }

    fun validateDiagonal(sudoku: Array<ByteArray>) : Boolean {
        if(validateNormal(sudoku).not()) {
            return false
        }
        val x1 = ByteArray(9)
        val x2 = ByteArray(9)
        for(i in 0..8) {
            x1[i] = sudoku[i][i]
            x2[i] = sudoku[8-i][i]
        }
        if(x1.distinct().size != 9 || x2.distinct().size != 9) {
            return false
        }
        return true
    }

    /**
     * Checks if the given sudoku fits the samurai sudoku constraints
     * @return A boolean if the given sudoku(s) is(are) valid
     * @since 1.0
     * @param sudokus A list of sudokus where the first sudoku is top-left, second is top-right, third is middle, fourth is bottom-left, fifth is bottom-right!
     */
    fun validateSamurai(sudokus: List<Array<ByteArray>>) : Boolean {
        for(sudoku in sudokus) {
            if (validateNormal(sudoku).not()) {
                return false
            }
        }
        log.debug("Compare First: ${compareFirst(sudokus[0],sudokus[2])}")
        log.debug("Compare Second: ${compareSecond(sudokus[1],sudokus[2])}")
        log.debug("Compare Fourth: ${compareFourth(sudokus[3],sudokus[2])}")
        log.debug("Compare Fifth: ${compareFifth(sudokus[4],sudokus[2])}")

        return compareFirst(sudokus[0],sudokus[2]) && compareSecond(sudokus[1],sudokus[2]) && compareFourth(sudokus[3],sudokus[2]) && compareFifth(sudokus[4],sudokus[2])
    }

    private fun compareFirst(first: Array<ByteArray>, third: Array<ByteArray>) : Boolean {
        return (
            first[6][6] == third[0][0] &&
            first[6][7] == third[0][1] &&
            first[6][8] == third[0][2] &&
            first[7][6] == third[1][0] &&
            first[7][7] == third[1][1] &&
            first[7][8] == third[1][2] &&
            first[8][6] == third[2][0] &&
            first[8][7] == third[2][1] &&
            first[8][8] == third[2][2]
        )
    }

    private fun compareSecond(second: Array<ByteArray>, third: Array<ByteArray>) : Boolean {
        return (
            second[6][0] == third[0][6] &&
            second[6][1] == third[0][7] &&
            second[6][2] == third[0][8] &&
            second[7][0] == third[1][6] &&
            second[7][1] == third[1][7] &&
            second[7][2] == third[1][8] &&
            second[8][0] == third[2][6] &&
            second[8][1] == third[2][7] &&
            second[8][2] == third[2][8]
        )
    }

    private fun compareFourth(fourth: Array<ByteArray>, third: Array<ByteArray>) : Boolean {
        return (
            fourth[0][6] == third[6][0] &&
            fourth[0][7] == third[6][1] &&
            fourth[0][8] == third[6][2] &&
            fourth[1][6] == third[7][0] &&
            fourth[1][7] == third[7][1] &&
            fourth[1][8] == third[7][2] &&
            fourth[2][6] == third[8][0] &&
            fourth[2][7] == third[8][1] &&
            fourth[2][8] == third[8][2]
        )
    }

    private fun compareFifth(fifth: Array<ByteArray>, third: Array<ByteArray>) : Boolean {
        return (
            fifth[0][0] == third[6][6] &&
            fifth[0][1] == third[6][7] &&
            fifth[0][2] == third[6][8] &&
            fifth[1][0] == third[7][6] &&
            fifth[1][1] == third[7][7] &&
            fifth[1][2] == third[7][8] &&
            fifth[2][0] == third[8][6] &&
            fifth[2][1] == third[8][7] &&
            fifth[2][2] == third[8][8]
        )
    }
}