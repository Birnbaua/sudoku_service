package at.birnbaua.sudoku_service.jpa.user

import at.birnbaua.sudoku_service.jpaservice.JpaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.*

@Entity
@Table(name = "`name`")
@Suppress("unused")
class Role(

    @Id
    @Column(name = "`name`")
    var name: String? = null,

    @Column(name = "`desc`", length = 1024, nullable = true)
    var desc: String? = null
) {
    @PrePersist
    @PreUpdate
    private fun preSave() {
        name = name?.uppercase()
    }
}

@Repository
interface RoleRepository : JpaRepository<Role,String>

@Service
class RoleService(@Autowired val repo: RoleRepository) : JpaService<Role,String>(repo)