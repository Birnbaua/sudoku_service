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
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import javax.validation.Valid

/**
 * This controller is responsible for all user data interaction.
 * The [RequestMapping] is /user
 * @since 1.0
 * @author Andreas Bachl
 */
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
        return ResponseEntity.created(URI("/${user.username}")).body(us.insert(user))
    }

    /**
     * @return The private info of the currently authenticated user
     * @since 1.0
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    fun current(auth: Authentication) : ResponseEntity<PrivateUserInfo> {
        return ResponseEntity.ok(us.findPrivateUserInfoById(auth.name))
    }

    /**
     * @return The info of the requested user
     * @since 1.0
     * @param username The username of the requested user
     */
    @GetMapping("/{username}")
    fun get(@PathVariable username: String, auth: Authentication) : ResponseEntity<UserInfo> {
        return ResponseEntity.ok(
            if(pc.hasPrivateDetailsPermission(username,auth)) {
                us.findPrivateUserInfoById(username)
            } else {
                us.findUserInfoById(username)
            }
        )
    }

    /**
     * @return Saves the updated infos about the user
     * @since 1.0
     * @param username The username of the modified user
     * @param user An instance of class [User] with the filled fields which should be updated
     */
    @PreAuthorize("isAuthenticated() AND (#username==#auth.name OR hasRole('ROLE_ADMIN'))")
    @PutMapping("/{username}")
    fun put(@RequestBody user: User, @PathVariable username: String, auth: Authentication) : ResponseEntity<PrivateUserInfo> {
        user.username = username
        return ResponseEntity.ok(us.findPrivateUserInfoById(us.update(user).username!!))
    }

    /**
     * @return nothing.
     * @since 1.0
     * @param username The username of the requested user to be deleted
     */
    @PreAuthorize("isAuthenticated() AND (#username==#auth.name OR hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/{username}")
    fun delete(@PathVariable username: String, auth: Authentication) : ResponseEntity<Any> {
        us.deleteById(username)
        return ResponseEntity.ok().build()
    }

    /**
     * @return Saves the profile picture to the user
     * @since 1.0
     * @param username The username of the modified user's profile picture
     * @param image A file of class [MultipartFile] which should be set as the new profile picture
     */
    @PreAuthorize("isAuthenticated() AND (#username==#auth.name OR hasRole('ROLE_ADMIN'))")
    @PostMapping("/{username}/picture")
    fun  uploadProfilePicture(@PathVariable username: String, @RequestParam image: MultipartFile) : ResponseEntity<PrivateUserInfo> {
        return ResponseEntity.ok(us.updateProfilePicture(username,image))
    }
}