package at.birnbaua.sudoku_service.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already existing!")
class UserAlreadyExistsException(msg: String) : RuntimeException(msg) {
}