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
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

/**
 * This service handles the generation and refreshing of JWTs.
 * @since 1.0
 * @author Andreas Bachl
 */
@Service
@Suppress("unused")
@EnableConfigurationProperties(JwtProperties::class)
class AuthService {

    @Autowired
    private lateinit var us: UserService

    @Autowired
    private lateinit var ts: TokenService

    /**
     * @return a valid JWT
     * @since 1.0
     * @throws [UserNotFoundException]
     */
    fun genToken(username: String, password: String) : JWTToken {
        val user = us.findUserByUsername(username)
        if(BCryptPasswordEncoder().matches(password.subSequence(0,password.length),user.password)) {
            return JWTToken(ts.genToken(username))
        }
        throw UserNotFoundException("No user with username: <$username> present")
    }

    fun genRefreshToken(username: String) : JWTToken {
        if(us.existsById(username)) {
            return JWTToken(ts.genToken(username))
        }
        throw UserNotFoundException("No user with username: <$username> present")
    }

    /**
     * @return a valid JWT
     * @since 1.0
     * @throws [TokenTimeoutException]
     * @param token a JWT
     */
    fun refresh(token: String) : JWTToken {
        if(ts.validateToken(token)) {
            return genRefreshToken(ts.getUsernameFromToken(token))
        }
        throw TokenTimeoutException()
    }
}