package at.birnbaua.sudoku_service.auth.user.jpa.entity

import javax.persistence.*

@Entity
@Table(name = "`role`")
class Role(

    @Id
    @Column(name = "`name`")
    var name: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges", joinColumns = [JoinColumn(name = "`role`", referencedColumnName = "name")], inverseJoinColumns = [JoinColumn(name = "privilege", referencedColumnName = "name")])
    var privileges: MutableSet<Privilege> = mutableSetOf()
)
