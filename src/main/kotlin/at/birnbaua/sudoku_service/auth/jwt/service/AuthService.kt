package at.birnbaua.sudoku_service.auth.jwt.service

import at.birnbaua.sudoku_service.auth.jwt.JWTToken
import at.birnbaua.sudoku_service.auth.exception.TokenTimeoutException
import at.birnbaua.sudoku_service.auth.exception.UserNotFoundException
import at.birnbaua.sudoku_service.auth.user.jpa.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Suppress("unused")
class AuthService {

    @Autowired
    lateinit var ur: UserRepository

    @Autowired
    lateinit var ts: TokenService

    fun genToken(username: String, password: String = "") : JWTToken {
        val opt = ur.findUserByUsername(username)
        if (opt.isPresent) {
            if(BCryptPasswordEncoder().matches(password.subSequence(0,password.length),opt.get().password)) {
                return JWTToken(ts.genToken(username))
            }
        }
        throw UserNotFoundException("No user with username: <$username> present")
    }

    fun refresh(token: JWTToken) : JWTToken {
        if(ts.getExpiryDateFromToken(token.token) < Date()) {
            return genToken(ts.getUsernameFromToken(token.token))
        }
        throw TokenTimeoutException()
    }
}