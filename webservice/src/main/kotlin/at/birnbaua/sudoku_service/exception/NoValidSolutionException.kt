package at.birnbaua.sudoku_service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception for invalid sudoku formats with [ResponseStatus] for exception handling in the RestControllers.
 * @since 1.0
 * @author Andreas Bachl
 * @return [HttpStatus] 200
 */
@ResponseStatus(code = HttpStatus.OK, reason = "Given sudoku has no valid solution!")
class NoValidSolutionException(msg: String) : RuntimeException(msg)
