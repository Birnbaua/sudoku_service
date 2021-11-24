package at.birnbaua.sudoku_service.jpa.sudoku

import at.birnbaua.sudoku_service.jpa.AbstractEntity
import at.birnbaua.sudoku_service.jpaservice.JpaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "`sudoku_type`")
open class SudokuType(

    @Id
    @Column(name = "`name`", length = 32)
    open var name: String? = null,

    @Column(name = "`desc`", length = 1024, nullable = true)
    open var desc: String? = null

) : AbstractEntity()

@Repository
interface SudokuTypeRepository : JpaRepository<SudokuType,String>

@Service
class SudokuTypeService(@Autowired repo: SudokuTypeRepository) : JpaService<SudokuType,String>(repo)