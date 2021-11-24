package at.birnbaua.sudoku_service.validation

import org.slf4j.Logger
import at.birnbaua.sudoku_service.exception.InvalidSudokuException
import org.slf4j.LoggerFactory

class SudokuValidation {

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
}