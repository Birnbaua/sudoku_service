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

@CrossOrigin
@RestController
@RequestMapping("/role")
class RoleController {

    @Autowired
    private lateinit var rs: RoleService

    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_USER_ROLE')")
    @PostMapping
    fun post(@RequestBody @Valid role: Role, auth: Authentication) : ResponseEntity<Role> {
        return ResponseEntity.created(URI("/${role.name}")).body(rs.save(role))
    }

    @GetMapping
    fun all() : ResponseEntity<MutableList<Role>> {
        return ResponseEntity.ok(rs.findAll())
    }

    @GetMapping("/{role}")
    fun get(@PathVariable role: String, auth: Authentication) : ResponseEntity<Role> {
        return ResponseEntity.ok(rs.findById(role))
    }

    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_USER_ROLE')")
    @PutMapping("/{name}")
    fun put(@RequestBody @Valid role: Role, @PathVariable name: String, auth: Authentication) : ResponseEntity<Role> {
        role.name = name
        return ResponseEntity.ok(rs.save(role))
    }

    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_USER_ROLE')")
    @DeleteMapping("/{role}")
    fun delete(@PathVariable role: String, auth: Authentication) : ResponseEntity<Any> {
        rs.deleteById(role)
        return ResponseEntity.ok().build()
    }

}