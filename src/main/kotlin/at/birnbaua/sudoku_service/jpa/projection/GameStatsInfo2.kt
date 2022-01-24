package at.birnbaua.sudoku_service.jpa.projection

import java.sql.Time
import java.sql.Timestamp

interface GameStatsInfo2 {
    val createdAt: Timestamp?
    val updatedAt: Timestamp?
    val duration: Time?
    val currentResult: String?
    val preview: ByteArray?
}