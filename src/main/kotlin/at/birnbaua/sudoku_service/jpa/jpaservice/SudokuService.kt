package at.birnbaua.sudoku_service.jpa.jpaservice

import at.birnbaua.sudoku_service.jpa.projection.SudokuGetInfo
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.jpa.repository.SudokuRepository
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class SudokuService @Autowired constructor(val rep: SudokuRepository) : JpaService<Sudoku, Int>(rep) {

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

    fun findByIdGetInfo(id: Int) : Optional<SudokuGetInfo> {
        return rep.findByIdGetInfo(id)
    }

    fun findSudokusByDifficulty(difficulty: Int, pageable: Pageable = PageRequest.of(0,30)) : Page<SudokuInfo> {
        return rep.findSudokusByDifficulty(Difficulty(difficulty),pageable)
    }
}