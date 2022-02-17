package at.birnbaua.sudoku_service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * User not authenticated exception with [ResponseStatus] for exception handling in the RestControllers. (If other spring security mechanisms are not wanted!)
 * @since 1.0
 * @author Andreas Bachl
 * @return [HttpStatus] 404
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User not authenticated!")
class UserNotAuthenticatedException(msg: String) : RuntimeException(msg)