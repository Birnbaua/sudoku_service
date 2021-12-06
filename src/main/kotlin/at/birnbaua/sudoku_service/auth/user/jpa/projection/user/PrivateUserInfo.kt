package at.birnbaua.sudoku_service.auth.user.jpa.projection.user

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege

interface PrivateUserInfo : UserInfo {
    val email: String?
    val roles: MutableSet<RoleInfo>

    interface RoleInfo {
        val name: String?
        val privileges: MutableSet<Privilege>
    }
}