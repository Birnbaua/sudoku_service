package at.birnbaua.sudoku_service.auth.user.jpa.entity

import javax.persistence.*

@Entity
@Table(name = "`privilege`")
class Privilege(

    @Id
    @Column(name = "`name`")
    var name: String? = null
)
