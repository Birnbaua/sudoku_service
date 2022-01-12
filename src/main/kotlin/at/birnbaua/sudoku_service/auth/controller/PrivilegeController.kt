package at.birnbaua.sudoku_service.auth.controller

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege
import at.birnbaua.sudoku_service.auth.user.jpa.service.PrivilegeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/privilege")
class PrivilegeController {

    @Autowired
    private lateinit var ps: PrivilegeService

    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_PRIVILEGE')")
    @PostMapping
    fun post(@RequestBody @Valid privilege: Privilege, auth: Authentication) : ResponseEntity<Privilege> {
        return ResponseEntity.created(URI("/${privilege.name}")).body(ps.save(privilege))
    }

    @PreAuthorize("hasPermission(#auth,#role,'VIEW_PRIVILEGE')")
    @GetMapping
    fun all() : ResponseEntity<MutableList<Privilege>> {
        return ResponseEntity.ok(ps.findAll())
    }

    @PreAuthorize("hasPermission(#auth,#role,'VIEW_PRIVILEGE')")
    @GetMapping("/{privilege}")
    fun get(@PathVariable privilege: String, auth: Authentication) : ResponseEntity<Privilege> {
        return ResponseEntity.ok(ps.findById(privilege))
    }

    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_PRIVILEGE')")
    @PutMapping("/{name}")
    fun put(@RequestBody @Valid privilege: Privilege, @PathVariable name: String, auth: Authentication) : ResponseEntity<Privilege> {
        privilege.name = name
        return ResponseEntity.ok(ps.save(privilege))
    }

    @PreAuthorize("hasPermission(#auth,#role,'MODIFY_PRIVILEGE')")
    @DeleteMapping("/{privilege}")
    fun delete(@PathVariable privilege: String, auth: Authentication) : ResponseEntity<Any> {
        ps.deleteById(privilege)
        return ResponseEntity.ok().build()
    }
}