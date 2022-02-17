package at.birnbaua.sudoku_service.jpa.entity.gamestats

import java.io.Serializable

data class GameStatsId(
    var sudoku: Int? = null,
    var user: String? = null
) : Serializable