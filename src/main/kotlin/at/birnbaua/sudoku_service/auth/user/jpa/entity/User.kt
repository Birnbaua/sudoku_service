package at.birnbaua.sudoku_service.auth.user.jpa.entity

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "`user`", indexes = [Index(columnList = "`username`", unique = true, name = "`username_index`")])
open class User(

    @Id
    @NotNull
    @Column(name = "`username`", unique = true, length = 64)
    @get:JvmName ("getUserUsername") open var username: String? = null,

    @NotNull
    @Column(name = "`nickname`", unique = true, length = 64, nullable = false)
    open var nickname: String? = null,

    @NotNull
    @Column(name = "`password`", nullable = false)
    @get:JvmName("getUserPassword") open var password: String? = null,

    @Email
    @NotNull
    @Column(name = "`email`", unique = true, nullable = false)
    open var email: String? = null,

    @Column(name = "`first_name`")
    open var firstName: String? = null,

    @Column(name = "`last_name`")
    open var lastName: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = [JoinColumn(name = "user", referencedColumnName = "username")], inverseJoinColumns = [JoinColumn(name = "role", referencedColumnName = "name")])
    open var roles: MutableSet<Role> = mutableSetOf()
) {
    constructor(user: User) : this(user.username,user.nickname,user.password,user.email,user.firstName,user.lastName,user.roles)

    @PrePersist
    private fun initSave() {
        if(nickname == null) {
            this.nickname = this.username
        }
    }
}
