package at.birnbaua.sudoku_service.jpa.jpaservice

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.projection.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.jpa.repository.SudokuRepository
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.thymeleaf.SudokuPreviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class SudokuService @Autowired constructor(val rep: SudokuRepository) : JpaService<Sudoku, Int>(rep) {

    @Autowired
    private lateinit var sps: SudokuPreviewService

    fun overview(difficulty: Int?, page: Int?, size: Int?) : Page<SudokuInfo> {
        var requestPage: Int? = page
        var requestSize: Int? = size
        if(page == null) {
            requestPage = 0
        }
        if(size == null) {
            requestSize = 30
        }
        return if(difficulty != null) {
            rep.overview(difficulty, PageRequest.of(requestPage!!,requestSize!!))
        } else {
            rep.overview(PageRequest.of(requestPage!!,requestSize!!))
        }
    }

    override fun saveAll(entities: MutableIterable<Sudoku>) : MutableList<Sudoku> {
        for(entity in entities) {
            if(entity.unsolved != null) {
                entity.preview = sps.toImage(sps.parseThymeleafTemplate(entity.unsolved!!))
            }
        }
        return super.saveAll(entities)
    }

    override fun saveAllAndFlush(entities: MutableIterable<Sudoku>) : MutableList<Sudoku> {
        for(entity in entities) {
            if(entity.unsolved != null) {
                entity.preview = sps.toImage(sps.parseThymeleafTemplate(entity.unsolved!!))
            }
        }
        return super.saveAllAndFlush(entities)
    }

    fun findAllByType(type: SudokuType, page: Int = 0, size: Int = 10) : Page<Sudoku> {
        return rep.findAllByType(type,PageRequest.of(page,size))
    }

    override fun save(entity: Sudoku) : Sudoku {
        if(entity.unsolved != null) {
            entity.preview = sps.toImage(sps.parseThymeleafTemplate(entity.unsolved!!))
        }
        return super.save(entity)
    }

    fun findByIdGetInfo(id: Int) : Optional<SudokuGetInfo> {
        return rep.findByIdGetInfo(id)
    }

    fun findSudokusByDifficulty(difficulty: Int, pageable: Pageable = PageRequest.of(0,30)) : Page<SudokuInfo> {
        return rep.findSudokusByDifficulty(Difficulty(difficulty),pageable)
    }

    fun existsSudokuByIdAndOwner(id: Int, user: User) : Boolean {
        return rep.existsSudokuByIdAndOwner(id,user)
    }
}