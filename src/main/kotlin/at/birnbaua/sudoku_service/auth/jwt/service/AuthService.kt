package at.birnbaua.sudoku_service.auth.jwt.service

import at.birnbaua.sudoku_service.auth.jwt.JWTToken
import at.birnbaua.sudoku_service.auth.exception.TokenTimeoutException
import at.birnbaua.sudoku_service.auth.exception.UserNotFoundException
import at.birnbaua.sudoku_service.auth.user.jpa.repository.UserRepository
import at.birnbaua.sudoku_service.auth.jwt.properties.JwtProperties
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Suppress("unused")
@EnableConfigurationProperties(JwtProperties::class)
class AuthService {

    @Autowired
    lateinit var us: UserService

    @Autowired
    lateinit var ts: TokenService

    fun genToken(username: String, password: String = "") : JWTToken {
        val user = us.findUserByUsername(username)
        if(BCryptPasswordEncoder().matches(password.subSequence(0,password.length),user.password)) {
            return JWTToken(ts.genToken(username))
        }
        throw UserNotFoundException("No user with username: <$username> present")
    }

    fun refresh(token: String) : JWTToken {
        if(ts.getExpiryDateFromToken(token) < Date()) {
            return genToken(ts.getUsernameFromToken(token))
        }
        throw TokenTimeoutException()
    }
}