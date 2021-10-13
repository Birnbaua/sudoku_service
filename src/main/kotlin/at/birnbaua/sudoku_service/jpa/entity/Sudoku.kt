package at.birnbaua.sudoku_service.jpa.entity

import at.birnbaua.sudoku_service.jpa.service.AbstractService
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.sql.Time
import javax.persistence.*

@Table(name = "`sudoku`")
@Entity
class Sudoku (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    var id: Int? = null,

    @Enumerated(EnumType.STRING)
    var level: Level? = null,

    @Column(name = "`solution`")
    var solution: String? = null,

    @Column(name = "`start`")
    var unsolved: String? = null,

    @Column(name = "`duration`")
    var duration: Time? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "`creator`")
    var creator: User

)

enum class Level(val lvl:Byte) {
    EASY(0),MODERATE(1),INTERMEDIATE(2),CHALLENGING(3),DIFFICULT(4),UNSOLVABLE(5)
}

@Repository
interface SudokuRepository : JpaRepository<Sudoku,Int>

@Service
class SudokuService(@Autowired repo: SudokuRepository) : AbstractService<Sudoku,Int>(repo)