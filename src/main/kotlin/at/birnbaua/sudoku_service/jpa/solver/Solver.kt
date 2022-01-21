package at.birnbaua.sudoku_service.jpa.solver

import at.birnbaua.sudoku_service.exception.NoValidSolutionException
import at.birnbaua.sudoku_service.jpa.entity.sudoku.validation.SudokuValidation
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class Solver {

    @Cacheable("solved")
    fun solveNormal(id: Int, sudoku: String) : String {
        return Companion.solveNormal(sudoku)
    }

    companion object {

        private val empty = "0".toByte()

        fun solveNormal(solve: String) : String {
            SudokuValidation.validateUnfinishedStructure(solve)
            val arr = SudokuValidation.to2DArray(solve)
            if(solve(arr)) {
                return arr.map { x -> x.asList() }.flatten().toList().joinToString().replace(", ","")
            } else {
                throw NoValidSolutionException("There is no valid solution for the given sudoku")
            }
        }

        private fun solve(solve: Array<ByteArray>) : Boolean {
            for (row in 0..8) {
                for (column in 0..8) {
                    if(solve[row][column] == empty) {
                        for (i in 1..9) {
                            solve[row][column] = i.toByte()
                            if(isCellValid(solve,row,column)) {
                                if(solve(solve)) {
                                    return true
                                }
                            }
                            solve[row][column] = empty
                        }
                        return false
                    }
                }
            }
            return true
        }

        private fun isCellValid(sudoku: Array<ByteArray>, row: Int, column: Int) : Boolean {
            return incompleteRowValid(sudoku, row) && incompleteColumnValid(sudoku, column) && incompleteSubsectionValid(sudoku,row/3,column/3)
        }

        fun incompleteSubsectionValid(sudoku: Array<ByteArray>, cellRow: Int, cellColumn: Int) : Boolean {
            var emptyCells = 0
            val arr = sudoku.filterIndexed{ index, x -> index/3 == cellRow}.map { x ->
                x.copyOfRange(cellColumn*3,cellColumn*3+3)
            }.flatMap { x -> x.asList()
            }.toByteArray()
            arr.forEach {x -> if(x==empty) {emptyCells = emptyCells.inc()}}
            if(emptyCells != 0) {
                return arr.distinct().size == 9-emptyCells+1
            }
            return arr.distinct().size == 9
        }

        fun incompleteRowValid(sudoku: Array<ByteArray>, row: Int) : Boolean {
            val emtpyCells = sudoku[row].filter { x -> x == empty }.size
            if(emtpyCells != 0) {
                return sudoku[row].distinct().size == 9-emtpyCells+1
            }
            return sudoku[row].distinct().size == 9
        }

        fun incompleteColumnValid(sudoku: Array<ByteArray>, column: Int) : Boolean {
            val emtpyCells = sudoku.map { x -> x[column] }.filter { x -> x == empty }.size
            if(emtpyCells != 0) {
                return sudoku.map { x -> x[column] }.distinct().size == 9-emtpyCells+1
            }
            return sudoku.map { x -> x[column] }.distinct().size == 9
        }

        private fun printSudoku(sudoku: Array<ByteArray>) {
            sudoku.forEach { s ->
                s.forEach { c -> print("$c ") }
                println()
            }
        }

    }
}