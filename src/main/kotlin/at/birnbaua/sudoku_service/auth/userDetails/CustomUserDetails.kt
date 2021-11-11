package at.birnbaua.sudoku_service.auth.userDetails

import at.birnbaua.sudoku_service.auth.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import kotlin.streams.toList

class CustomUserDetails(private val user: User) :  UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val list: MutableList<GrantedAuthority> = mutableListOf()
        list.plus(user.roles.stream().map { x -> SimpleGrantedAuthority("ROLE_${x.name?.uppercase()}") }.toList())
        return list
    }

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getUsername(): String {
        return user.username!!
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}