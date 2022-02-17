package at.birnbaua.sudoku_service.auth.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege
import at.birnbaua.sudoku_service.auth.user.jpa.entity.Role
import at.birnbaua.sudoku_service.auth.user.jpa.service.PrivilegeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

/**
 * This controller is responsible for all [Privilege] specific methods.
 * The [RequestMapping] is /privilege
 * @since 1.0
 * @author Andreas Bachl
 */
@CrossOrigin
@RestController
@RequestMapping("/privilege")
class PrivilegeController {

    @Autowired
    private lateinit var ps: PrivilegeService

    /**
     * @return a new saved privilege
     * @since 1.0
     * @param privilege Privilege which should be saved
     */
    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_PRIVILEGE')")
    @PostMapping
    fun post(@RequestBody @Valid privilege: Privilege, auth: Authentication) : ResponseEntity<Privilege> {
        return ResponseEntity.created(URI("/${privilege.name}")).body(ps.save(privilege))
    }

    /**
     * @return all available privileges
     * @since 1.0
     */
    @PreAuthorize("hasPermission(#auth,#role,'VIEW_PRIVILEGE')")
    @GetMapping
    fun all() : ResponseEntity<MutableList<Privilege>> {
        return ResponseEntity.ok(ps.findAll())
    }

    /**
     * @return requested privilege
     * @since 1.0
     * @param privilege name of requested privilege
     */
    @PreAuthorize("hasPermission(#auth,#role,'VIEW_PRIVILEGE')")
    @GetMapping("/{privilege}")
    fun get(@PathVariable privilege: String, auth: Authentication) : ResponseEntity<Privilege> {
        return ResponseEntity.ok(ps.findById(privilege))
    }

    /**
     * @return Modified privilege. If privilege is not existing created privilege with given specs
     * @since 1.0
     * @param name ID of privilege which should be modified or created
     * @param privilege Modified version of privilege
     */
    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_PRIVILEGE')")
    @PutMapping("/{name}")
    fun put(@RequestBody @Valid privilege: Privilege, @PathVariable name: String, auth: Authentication) : ResponseEntity<Privilege> {
        privilege.name = name
        return ResponseEntity.ok(ps.save(privilege))
    }

    /**
     * Deletes privilege with given ID. If not existing no exception is thrown.
     * @return nothing.
     * @since 1.0
     * @param privilege ID of privilege which should be deleted
     */
    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_PRIVILEGE')")
    @DeleteMapping("/{privilege}")
    fun delete(@PathVariable privilege: String, auth: Authentication) : ResponseEntity<Any> {
        ps.deleteById(privilege)
        return ResponseEntity.ok().build()
    }
}