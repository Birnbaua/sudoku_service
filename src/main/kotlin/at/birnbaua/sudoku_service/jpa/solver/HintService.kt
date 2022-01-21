package at.birnbaua.sudoku_service.jpa.solver

import at.birnbaua.sudoku_service.exception.SudokuNotExistingException
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class HintService {

    private val empty = 0.toByte()
    private val log = LoggerFactory.getLogger(HintService::class.java)
    private val random = Random(System.currentTimeMillis())

    @Autowired
    private lateinit var solver: Solver

    @Autowired
    private lateinit var ss: SudokuService

    fun getSolved(id: Int, sudoku: String) : String {
        return solver.solveNormal(id, sudoku)
    }

    fun getHint(id: Int, sudoku: String) : Hint {
        val stored = ss.findById(id).orElseThrow { SudokuNotExistingException(id,log) }
        if(!compareUnsolvedAndSolvedSudoku(stored.unsolved!!,sudoku)) RuntimeException("Wrong Sudoku requested!")
        val arr = emptyCellArray(sudoku)
        val no = random.nextInt(arr.size)
        return Hint(
            (no/9).toByte(),
            (no%9).toByte(),
            solver.solveNormal(id,sudoku)[arr[no]].code.toByte()
        )
    }

    private fun emptyCellArray(sudoku: String) : List<Int> {
        return sudoku.mapIndexed { index, c ->
            if(c.code.toByte() == empty) {
                index
            } else {
                -1
            }
        }.filter { x -> x > -1 }.toList()
    }

    /**
     * Compares if cached version of sudoku differs from requested one to save cpu time.
     */
    private fun compareUnsolvedAndSolvedSudoku(unsolved: String, solved: String) : Boolean {
        return unsolved.filterIndexed{ index, c ->
            if(c.code.toByte() == empty) {
                true
            } else {
                c == solved[index]
            }.not()
        }.isEmpty()
    }

}