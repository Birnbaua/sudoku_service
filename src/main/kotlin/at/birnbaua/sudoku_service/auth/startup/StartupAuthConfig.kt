package at.birnbaua.sudoku_service.auth.startup

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege
import at.birnbaua.sudoku_service.auth.user.jpa.entity.Role
import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.PrivilegeService
import at.birnbaua.sudoku_service.auth.user.jpa.service.RoleService
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableScheduling
class StartupAuthConfig {

    @Autowired
    lateinit var rs: RoleService

    @Autowired
    lateinit var us: UserService

    @Autowired
    lateinit var ps: PrivilegeService

    @Scheduled(initialDelay = 500, fixedRate = 1000*60*9999)
    fun startup() {
        val modifyPrivilege = Privilege("MODIFY_PRIVILEGE")
        val viewPrivilege = Privilege("VIEW_PRIVILEGE")
        val modifyRolePrivilege = Privilege("MODIFY_USER_ROLE")
        val viewRolePrivilege = Privilege("VIEW_USER_ROLE")

        ps.save(modifyPrivilege)
        ps.save(viewPrivilege)
        ps.save(modifyRolePrivilege)
        ps.save(viewRolePrivilege)

        val adminRole = Role("ADMIN")
        val guestRole = Role("GUEST")

        adminRole.privileges.add(modifyPrivilege)
        adminRole.privileges.add(viewPrivilege)
        adminRole.privileges.add(modifyRolePrivilege)
        adminRole.privileges.add(viewRolePrivilege)
        guestRole.privileges.add(viewPrivilege)
        guestRole.privileges.add(viewRolePrivilege)

        rs.save(adminRole)
        rs.save(guestRole)

        if(us.existsById("admin")) {
            val admin = User()
            admin.username = "admin"
            admin.nickname = "admin"
            admin.firstName = "Max"
            admin.lastName = "Mustermann"
            admin.email = "max.mustermann@example.com"
            admin.roles.add(adminRole)
            admin.roles.add(guestRole)
            admin.password = BCryptPasswordEncoder(8).encode("admin")
            us.update(admin)
        }
    }
}