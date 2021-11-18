package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.auth.config.MyEncoder
import at.birnbaua.sudoku_service.jpa.user.User
import at.birnbaua.sudoku_service.jpa.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired
    lateinit var us: UserService

    @PostMapping("/register")
    fun register(@RequestBody user: User) : ResponseEntity<User> {
        user.password = MyEncoder().encode(user.password?.subSequence(0, user.password!!.length))
        return ResponseEntity.status(HttpStatus.CREATED).body(us.save(user))
    }
}