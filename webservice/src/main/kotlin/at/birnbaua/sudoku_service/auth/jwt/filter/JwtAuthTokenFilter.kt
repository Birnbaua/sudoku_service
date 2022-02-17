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

/**
 * Request filter for all requests.
 * @since 1.0
 * @author Andreas Bachl
 */
@Component
@EnableConfigurationProperties(JwtProperties::class)
class JwtAuthTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    /**
     * If a token is present, it validates the token and puts an authentication object into the [SecurityContextHolder]
     * If no token is present, the filter will put the request/response without any work to the filterchain.
     * @since 1.0
     * @author Andreas Bachl
     */
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