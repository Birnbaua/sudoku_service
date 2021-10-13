package at.birnbaua.sudoku_service.jpa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Size


@Table(name = "`cell`")
@Entity
@IdClass(CellId::class)
class Cell(

    @Id
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var sudoku: Sudoku? = null,

    @Id
    @Size(min = 0, max = 8)
    @Column(name = "`row`")
    var row: Byte? = null,

    @Id
    @Size(min = 0, max = 8)
    @Column(name = "`column`")
    var column: Byte? = null,

    @Size(min = 0, max = 8)
    @Column(name = "`group`", nullable = false)
    var group: Byte? = null,

    @Size(min = 0, max = 9) //0 means no value
    @Column(name = "`value`", nullable = true)
    var value: Byte = 0
)

data class CellId(
    var sudoku: Int?,
    var row: Byte?,
    var column: Byte?
) : Serializable

@Repository
interface CellRepository : JpaRepository<Cell,CellId> {
    fun findAllBySudoku(sudoku: Int?) : MutableSet<Cell>
}