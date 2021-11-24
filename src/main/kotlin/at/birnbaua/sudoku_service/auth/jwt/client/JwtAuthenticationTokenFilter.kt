package at.birnbaua.sudoku_service.auth.jwt.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter : OncePerRequestFilter() {

    @Value("\${jwt.header}")
    lateinit var header: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = request.getHeader(header)
        if(token.startsWith("$header ")) {
            token = token.substring(header.length)
            val jwtAuth = JwtAuthentication(token)
            SecurityContextHolder.getContext().authentication = jwtAuth
        }
        filterChain.doFilter(request,response)
    }
}