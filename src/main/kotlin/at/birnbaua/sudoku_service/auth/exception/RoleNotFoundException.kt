package at.birnbaua.sudoku_service.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Role not found!")
class RoleNotFoundException(name: String) : RuntimeException("Role with name: $name not found!") {
}