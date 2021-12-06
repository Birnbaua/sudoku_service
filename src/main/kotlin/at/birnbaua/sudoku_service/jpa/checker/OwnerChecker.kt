package at.birnbaua.sudoku_service.jpa.checker

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class OwnerChecker {

    @Autowired
    private lateinit var ss: SudokuService

    fun isSudokuOwner(sudokuId: Int, auth: Authentication) : Boolean {
        return ss.existsSudokuByIdAndOwner(sudokuId, User(auth.name))
    }
}