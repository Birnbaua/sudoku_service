package at.birnbaua.sudoku_service.exception

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Sudoku not existing!")
class SudokuNotExistingException(id: Int, log: Logger) : RuntimeException("Sudoku with id: $id is not existing in the database!") {
    init {
        log.warn(this.stackTraceToString())
    }
}