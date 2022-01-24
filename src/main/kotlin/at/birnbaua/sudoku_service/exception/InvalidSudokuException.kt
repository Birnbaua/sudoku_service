package at.birnbaua.sudoku_service.exception

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception for invalid sudoku formats with [ResponseStatus] for exception handling in the RestControllers.
 * @since 1.0
 * @author Andreas Bachl
 * @return [HttpStatus] 400
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid format of sudoku!")
class InvalidSudokuException(msg: String, log: Logger) : RuntimeException(msg) {
    init {
        log.warn(this.stackTraceToString())
    }
}