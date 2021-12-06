package at.birnbaua.sudoku_service.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not existing!")
class UserNotFoundException(username: String) : RuntimeException("User with username: <$username> not found!")