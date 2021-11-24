package at.birnbaua.sudoku_service.auth.jwt.client

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import kotlin.jvm.Throws

class JwtAuthentication(private val token: String) : Authentication {
    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getCredentials(): Any {
        return token
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }

    override fun isAuthenticated(): Boolean {
        return false
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(p0: Boolean) {

    }
}