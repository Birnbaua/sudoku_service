package at.birnbaua.sudoku_service.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Privilege not found!")
class PrivilegeNotFoundException(name: String) : RuntimeException("Privilege with name: $name not found!")