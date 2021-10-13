package at.birnbaua.sudoku_service.jpa.projection

import at.birnbaua.sudoku_service.jpa.entity.Level
import java.sql.Time

interface SudokuInfo {
    val id: Int?
    val duration: Time?
    val level: Level?
    val creator: UserInfo?

    interface UserInfo {
        val username: String?
    }
}