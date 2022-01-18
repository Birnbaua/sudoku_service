package at.birnbaua.sudoku_service.jpa.entity.sudoku.validation

import org.slf4j.Logger
import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SudokuValidation {

    @Autowired
    lateinit var ss: SudokuService
    private val log: Logger = LoggerFactory.getLogger(SudokuValidation::class.java)

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SudokuValidation::class.java)

        fun validateUnfinishedStructure(unsolved: String) {
            if(unsolved.matches("^[0-9]*\$".toRegex()).not()) {
                throw InvalidSudokuException("The unsolved Sudoku contains invalid characters. It may only contain digits from 0 to 9",
                    log
                )
            }
        }

        fun validateFinishedStructure(solved : String) : Boolean {
            if(solved.matches("^[1-9]*\$".toRegex()).not()) {
                throw InvalidSudokuException("The solved Sudoku contains invalid characters. It may only contain digits from 1 to 9",
                    log
                )
            }
            return true
        }

        fun to2DArray(sudoku: String) : Array<ByteArray> {
            val arr = Array(9) { ByteArray(9) }
            for(c in sudoku.toCharArray()?.withIndex()!!) {
                arr[c.index/9][c.index%9] = c.value.toString().toByte()
            }
            return arr
        }
    }


    fun validate(id: Int, check: String, type: SudokuType = SudokuType.NORMAL) : Boolean {
        if(validateFinishedStructure(check)) {
            return false
        }

        //check other constraints
        return when(type) {
            SudokuType.NORMAL -> validateNormal(to2DArray(check))
            SudokuType.DIAGONAL -> validateDiagonal(to2DArray(check))
            SudokuType.SAMURAI -> validateSamurai(check.split(";").map { x -> to2DArray(x) })
            else -> false
        }
    }

    private fun checkIfRightSudoku(id: Int, solved: Array<ByteArray>) : Boolean {
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

    private fun validateNormal(sudoku: Array<ByteArray>) : Boolean {
        for(i in 0..8) {
            if(sudoku[i].distinct().size != 9) {
                return false
            }
            val column = ByteArray(9)
            for(j in 0..8) {
                column[j] = sudoku[i][j]
            }
            if(column.distinct().size != 9) {
                return false
            }
        }
        return true
    }

    private fun validateDiagonal(sudoku: Array<ByteArray>) : Boolean {
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

    private fun validateSamurai(sudokus: List<Array<ByteArray>>) : Boolean {
        for(sudoku in sudokus) {
            if (validateNormal(sudoku).not()) {
                return false
            }
        }
        return compareFirst(sudokus[0],sudokus[2]) && compareSecond(sudokus[1],sudokus[2]) && comparefourth(sudokus[3],sudokus[2]) && compareFifth(sudokus[4],sudokus[2])
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

    private fun comparefourth(fourth: Array<ByteArray>, third: Array<ByteArray>) : Boolean {
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
                fifth[0][0] == third[6][0] &&
                        fifth[0][1] == third[6][1] &&
                        fifth[0][2] == third[6][2] &&
                        fifth[1][0] == third[7][0] &&
                        fifth[1][1] == third[7][1] &&
                        fifth[1][2] == third[7][2] &&
                        fifth[2][0] == third[8][0] &&
                        fifth[2][1] == third[8][1] &&
                        fifth[2][2] == third[8][2]
                )
    }


}