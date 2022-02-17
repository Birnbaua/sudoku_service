package at.birnbaua.sudoku_service.auth.user.permission

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

/**
 * Gimmick for using annotations in the Pre and PostAuthorize Annotations to use very custom methods for authorizing.
 * @since 1.0
 * @author Andreas Bachl
 */
@Service
class PermissionChecker {

    fun hasPrivateDetailsPermission(username: String, auth: Authentication) : Boolean  {
        return auth != null && (auth.name == username || auth.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")))
    }
}