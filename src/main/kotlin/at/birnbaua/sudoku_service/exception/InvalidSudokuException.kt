package at.birnbaua.sudoku_service.exception

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid format of sudoku!")
class InvalidSudokuException(msg: String, log: Logger) : RuntimeException(msg) {
    init {
        log.warn(this.stackTraceToString())
    }
}