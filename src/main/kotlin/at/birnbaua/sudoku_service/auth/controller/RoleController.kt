package at.birnbaua.sudoku_service.auth.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Role
import at.birnbaua.sudoku_service.auth.user.jpa.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

/**
 * This controller is responsible for all [Role] specific methods.
 * The [RequestMapping] is /role
 * @since 1.0
 * @author Andreas Bachl
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
class RoleController {

    @Autowired
    private lateinit var rs: RoleService

    /**
     * @return Saves a new Role for Users
     * @since 1.0
     * @param role Role which should be saved
     */
    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_USER_ROLE')")
    @PostMapping
    fun post(@RequestBody @Valid role: Role, auth: Authentication) : ResponseEntity<Role> {
        return ResponseEntity.created(URI("/${role.name}")).body(rs.save(role))
    }

    /**
     * @return all available roles
     * @since 1.0
     */
    @PreAuthorize("hasPermission(#auth,#role,'VIEW_USER_ROLE')")
    @GetMapping
    fun all() : ResponseEntity<MutableList<Role>> {
        return ResponseEntity.ok(rs.findAll())
    }

    /**
     * @return Role with privileges
     * @since 1.0
     * @param role name of requested role
     */
    @PreAuthorize("hasPermission(#auth,#role,'VIEW_USER_ROLE')")
    @GetMapping("/{role}")
    fun get(@PathVariable role: String, auth: Authentication) : ResponseEntity<Role> {
        return ResponseEntity.ok(rs.findById(role))
    }

    /**
     * @return Modified role with privileges. If role is not exisiting created new role with given specs
     * @since 1.0
     * @param name ID of role which should be modified or created
     * @param role Modified version of role
     */
    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_USER_ROLE')")
    @PutMapping("/{name}")
    fun put(@RequestBody @Valid role: Role, @PathVariable name: String, auth: Authentication) : ResponseEntity<Role> {
        role.name = name
        return ResponseEntity.ok(rs.save(role))
    }

    /**
     * Deletes role with given ID. If not existing no exception is thrown.
     * @return nothing.
     * @since 1.0
     * @param role ID of role which should be deleted
     */
    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_USER_ROLE')")
    @DeleteMapping("/{role}")
    fun delete(@PathVariable role: String, auth: Authentication) : ResponseEntity<*> {
        return ResponseEntity.ok(rs.deleteById(role))
    }

}