package at.birnbaua.sudoku_service.auth.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.projection.user.PrivateUserInfo
import at.birnbaua.sudoku_service.auth.user.jpa.projection.user.UserInfo
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.auth.user.permission.PermissionChecker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var us: UserService

    @Autowired
    private lateinit var pc: PermissionChecker

    @PostMapping
    fun post(@RequestBody @Valid user: User, auth: Authentication) : ResponseEntity<User> {
        return ResponseEntity.created(URI("/${user.username}")).body(us.save(user))
    }

    @GetMapping("/{username}")
    fun create(@PathVariable username: String, auth: Authentication) : ResponseEntity<UserInfo> {
        return ResponseEntity.ok(
            if(pc.hasPrivateDetailsPermission(username,auth)) {
                us.findPrivateUserInfoById(username)
            } else {
                us.findUserInfoById(username)
            }
        )
    }

    @PreAuthorize("isAuthenticated() AND (#username==#auth.name OR hasRole('ROLE_ADMIN'))")
    @PutMapping("/{username}")
    fun put(@RequestBody user: User, @PathVariable username: String, auth: Authentication) : ResponseEntity<PrivateUserInfo> {
        user.username = username
        return ResponseEntity.ok(us.findPrivateUserInfoById(us.save(user).username!!))
    }

    @PreAuthorize("isAuthenticated() AND (#username==#auth.name OR hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/{username}")
    fun delete(@PathVariable username: String, auth: Authentication) : ResponseEntity<Any> {
        us.deleteById(username)
        return ResponseEntity.ok().build()
    }
}