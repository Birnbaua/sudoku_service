package at.birnbaua.sudoku_service.jpa.sudoku

import at.birnbaua.sudoku_service.jpaservice.JpaService
import org.hibernate.annotations.NaturalId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
@Table(name = "`difficulty`")
@Suppress("unused")
class Difficulty(

    @Id
    @Min(0)
    @Column(name = "`no`")
    var no: Int? = null,

    @NaturalId
    @Max(32)
    @Column(name = "`name`", length = 32, nullable = false)
    var name: String? = null,

    @Column(name = "`desc`", length = 255, nullable = true)
    var desc: String? = null
)

@Repository
interface DifficultyRepository : JpaRepository<Difficulty,Int>

@Service
class DifficultyService @Autowired constructor(rep: DifficultyRepository) : JpaService<Difficulty, Int>(rep)