package at.birnbaua.sudoku_service.auth.service

import at.birnbaua.sudoku_service.auth.config.MyEncoder
import at.birnbaua.sudoku_service.auth.jwt.JWTToken
import at.birnbaua.sudoku_service.auth.userDetails.CustomUserDetailsService
import at.birnbaua.sudoku_service.jpa.user.User
import at.birnbaua.sudoku_service.jpa.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired
    lateinit var us: UserService

    @Autowired
    lateinit var ts: TokenService

    val encoder = MyEncoder()

    fun genToken(username: String, password: String) : JWTToken {
        val opt = us.findUserByUsername(username)
        if (opt.isPresent) {
            if(encoder.matches(password.subSequence(0,password.length),opt.get().password)) {
                return JWTToken(ts.genToken(username))
            }
        }
        throw RuntimeException("No user present")
    }
}