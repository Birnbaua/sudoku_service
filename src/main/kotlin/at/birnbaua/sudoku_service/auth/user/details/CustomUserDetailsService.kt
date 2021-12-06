package at.birnbaua.sudoku_service.auth.user.details

import at.birnbaua.sudoku_service.auth.exception.UserNotFoundException
import at.birnbaua.sudoku_service.auth.user.jpa.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var ur: UserRepository

    @Cacheable("users")
    override fun loadUserByUsername(username: String?): UserDetails {
        return CustomUserDetails(ur.findUserByUsername(username!!).orElseThrow { UserNotFoundException(username) })
    }
}