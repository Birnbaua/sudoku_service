package at.birnbaua.sudoku_service.jpa.entity

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class Ownership(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`owner`", nullable = true)
    open var owner: User? = null
) : AbstractEntity()