package at.birnbaua.sudoku_service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No entity with requested id found in database.")
class EntityNotFoundException(msg: String) : RuntimeException(msg)