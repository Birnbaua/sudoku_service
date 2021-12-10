package at.birnbaua.sudoku_service.auth.permission

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege
import at.birnbaua.sudoku_service.auth.user.jpa.entity.Role
import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CustomPermissionEvaluator : PermissionEvaluator {

    @Autowired
    private lateinit var us: UserService

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        if(authentication == null || targetDomainObject == null || permission == null) {
            return false
        }
        return when (targetDomainObject) {
            is User -> hasUserPermission(authentication, targetDomainObject,permission as String)
            is Role -> hasRolePermission(authentication,targetDomainObject,permission as String)
            is Privilege -> hasPrivilegePermission(authentication,targetDomainObject,permission as String)
            else -> false
        }
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        if(authentication == null || targetId == null || targetType == null || permission == null) {
            return false
        }
        return true
    }

    private fun hasPrivilegePermission(auth: Authentication, obj: Privilege, permission: String): Boolean {
        return hasPermission(auth,permission)
    }

    private fun hasRolePermission(auth: Authentication, obj: Role, permission: String): Boolean {
        return hasPermission(auth,permission)
    }

    private fun hasUserPermission(auth: Authentication, obj: User, permission: String) : Boolean {
        return hasPermission(auth,permission)
    }

    private fun hasPermission(auth: Authentication, permission: String) : Boolean {
        return auth.authorities.contains(SimpleGrantedAuthority(permission))
    }
}