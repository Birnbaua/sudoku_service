package at.birnbaua.sudoku_service.exception

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception for not existing sudoku in the database [ResponseStatus] for exception handling in the RestControllers.
 * @since 1.0
 * @author Andreas Bachl
 * @return [HttpStatus] 404
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Sudoku not existing!")
class SudokuNotExistingException(id: Int, log: Logger) : RuntimeException("Sudoku with id: $id is not existing in the database!") {
    init {
        log.warn(this.stackTraceToString())
    }
}