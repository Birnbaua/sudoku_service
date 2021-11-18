package at.birnbaua.sudoku_service.jpa.user

import at.birnbaua.sudoku_service.jpaservice.JpaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "`user`")
open class User(

    @Id
    @Column(name = "`username`")
    open var username: String? = null,

    @Column(name = "`password`")
    open var password: String? = null,

    @Column(name = "`first_name`")
    open var firstName: String? = null,

    @Column(name = "`last_name`")
    open var lastName: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "`user_role`", joinColumns = [JoinColumn(name = "`username`")], inverseJoinColumns = [JoinColumn(name = "`name`")])
    open var roles: MutableSet<Role> = mutableSetOf()
) {
    constructor(user: User) : this(user.username,user.password,user.firstName,user.lastName,user.roles)
}

@Repository
interface UserRepository : JpaRepository<User,String> {
    fun findUserByUsername(username: String) : Optional<User>
}

@Service
class UserService(@Autowired val repo: UserRepository) : JpaService<User,String>(repo) {

    fun findUserByUsername(username: String) : Optional<User> {
        return repo.findUserByUsername(username)
    }
}