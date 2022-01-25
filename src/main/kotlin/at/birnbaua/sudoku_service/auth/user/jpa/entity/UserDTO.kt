package at.birnbaua.sudoku_service.auth.user.jpa.entity

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class UserDTO(
    var username: String? = null,
    var nickname: String? = null,
    var password: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var profilePicture: ByteArray? = null,
    var roles: MutableSet<Role> = mutableSetOf()
)