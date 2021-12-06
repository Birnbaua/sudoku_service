package at.birnbaua.sudoku_service.auth.user.details

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(user: User) : User(user), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.roles.map { role -> "ROLE_${role.name}" }.map { str -> SimpleGrantedAuthority(str) }.toMutableSet()
    }

    override fun getPassword(): String {
        return this.password!!
    }

    override fun getUsername(): String {
        return this.username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    private fun toRoleSet() : MutableSet<GrantedAuthority> {
        return this.roles.map { role -> "ROLE_${role.name}" }.map { str -> SimpleGrantedAuthority(str) }.toMutableSet()
    }

    private fun toPrivilegeSet() : MutableSet<GrantedAuthority> {
        return this.roles.flatMap { role -> role.privileges }.map { privilege -> SimpleGrantedAuthority(privilege.name) }.toMutableSet()
    }
}