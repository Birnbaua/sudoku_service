package at.birnbaua.sudoku_service.auth.userDetails

import at.birnbaua.sudoku_service.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var us: UserService

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(p0: String?): UserDetails {
        return CustomUserDetails(us.findUserByUsername(p0!!).orElseThrow { UsernameNotFoundException("User with username $p0 not found!") })
    }
}