package at.birnbaua.sudoku_service.auth.user.jpa.service

import at.birnbaua.sudoku_service.auth.exception.UserNotFoundException
import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.projection.user.PrivateUserInfo
import at.birnbaua.sudoku_service.auth.user.jpa.projection.user.UserInfo
import at.birnbaua.sudoku_service.auth.user.jpa.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@Suppress("unused")
class UserService {

    @Autowired
    private lateinit var ur: UserRepository

    @CachePut(value = ["users"], key = "#user.getUserUsername")
    fun update(user: User) : User {
        return ur.save(user)
    }

    @CachePut(value = ["users"], key = "#user.getUserUsername")
    fun insert(user: User) : User {
        user.password = BCryptPasswordEncoder().encode(user.password)
        return ur.save(user)
    }


    @Cacheable("users")
    fun findUserByUsername(username: String) : User {
        return ur.findUserByUsername(username).orElseThrow { UserNotFoundException(username) }
    }

    @CacheEvict("users")
    fun deleteById(username: String) {
        try{
            ur.deleteById(username)
        } catch(e: Exception) {
            throw UserNotFoundException(username)
        }
    }

    fun findPrivateUserInfoById(username: String) : PrivateUserInfo {
        return ur.findPrivateUserInfo(username).orElseThrow { UserNotFoundException(username) }
    }

    fun findUserInfoById(username: String) : UserInfo {
        return ur.findUserInfo(username).orElseThrow { UserNotFoundException(username) }
    }

    fun existsById(username: String) : Boolean {
        return ur.existsById(username)
    }
}