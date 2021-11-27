package at.birnbaua.sudoku_service.validation

import org.slf4j.Logger
import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.sudoku.SudokuService
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

        fun validateFinishedStructure(solved : String) {
            if(solved.matches("^[1-9]*\$".toRegex()).not()) {
                throw InvalidSudokuException("The solved Sudoku contains invalid characters. It may only contain digits from 1 to 9",
                    log
                )
            }
        }
    }


    fun validate(id: Int, check: String) : Boolean {
        val sudoku: Sudoku = ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }
        val arr = Array(9) { ByteArray(9) }
        sudoku.unsolved?.chars()!!
        for(c in sudoku.unsolved?.chars()!!) {
            arr[][]
        }
        return false
    }
}