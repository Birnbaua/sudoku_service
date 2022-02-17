package at.birnbaua.sudoku_service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * General exception for missing entity with [ResponseStatus] for exception handling in the RestControllers.
 * @since 1.0
 * @author Andreas Bachl
 * @return [HttpStatus] 404
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No entity with requested id found in database.")
class EntityNotFoundException(msg: String) : RuntimeException(msg)