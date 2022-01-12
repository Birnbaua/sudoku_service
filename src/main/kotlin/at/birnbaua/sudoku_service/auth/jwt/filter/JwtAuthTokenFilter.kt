package at.birnbaua.sudoku_service.auth.jwt.filter

import at.birnbaua.sudoku_service.auth.user.details.CustomUserDetailsService
import at.birnbaua.sudoku_service.auth.jwt.service.TokenService
import at.birnbaua.sudoku_service.auth.jwt.properties.JwtProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
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
@EnableConfigurationProperties(JwtProperties::class)
class JwtAuthTokenFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var jwtProperties: JwtProperties

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = request.getHeader(jwtProperties.header)
        if (token != null) {
            token = token.replace(jwtProperties.prefix,"")
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