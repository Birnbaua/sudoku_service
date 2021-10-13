package at.birnbaua.sudoku_service.jpa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.NaturalId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email

@Table
@Entity
class User(

    @Id
    @Column(name = "`username`", length = 32)
    var username: String? = null,

    @JsonIgnore
    @Column(name = "`password`")
    var password: String? = null,

    @Email
    @NaturalId
    @Column(name = "`email`")
    var email: String? = null

)

@Repository
interface UserRepository : JpaRepository<User,String> {
    fun findByUsername(username: String) : User?
    fun findByEmail(email: String) : User?
}