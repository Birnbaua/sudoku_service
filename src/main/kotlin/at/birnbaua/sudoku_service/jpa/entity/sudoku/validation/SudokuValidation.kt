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
        val solved = to2DArray(check)

        if(validateFinishedStructure(check) || checkIfRightSoduku(id,solved)) {
            return false
        }

        //check other constraints
        return when(type) {
            SudokuType.NORMAL -> validateNormal(solved)
            SudokuType.SAMURAI -> validateSamurai(solved)
            else -> false
        }
    }

    private fun checkIfRightSoduku(id: Int, solved: Array<ByteArray>) : Boolean {
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

    private fun validateSamurai(sudoku: Array<ByteArray>) : Boolean {
        if(validateNormal(sudoku)) {
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
}