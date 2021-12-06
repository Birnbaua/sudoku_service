package at.birnbaua.sudoku_service.auth.jwt.filter

import at.birnbaua.sudoku_service.auth.user.details.CustomUserDetailsService
import at.birnbaua.sudoku_service.auth.jwt.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthTokenFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var userDetailsService: CustomUserDetailsService

    @Value("\${jwt.header}")
    lateinit var header: String

    //    @Value(value = "\${jwt.prefix}")
    var prefix: String = "Bearer "

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = request.getHeader(header)
        if (token != null) {
            token = token.replace(prefix,"")
            if(tokenService.validateToken(token)) {
                val username: String = tokenService.getUsernameFromToken(token)
                val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }
}