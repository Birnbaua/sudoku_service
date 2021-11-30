package at.birnbaua.sudoku_service.auth.secure

import at.birnbaua.sudoku_service.auth.jwt.client.service.TokenServiceClient
import at.birnbaua.sudoku_service.jpa.gamestats.GameStatsId
import at.birnbaua.sudoku_service.jpa.gamestats.GameStatsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class OwnerCheck {

    @Autowired
    lateinit var gss: GameStatsService

    @Autowired
    lateinit var tsc: TokenServiceClient

}