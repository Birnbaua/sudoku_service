package at.birnbaua.sudoku_service.auth.config

import at.birnbaua.sudoku_service.auth.jwt.filter.JwtAuthEntryPoint
import at.birnbaua.sudoku_service.auth.jwt.filter.JwtAuthTokenFilter
import at.birnbaua.sudoku_service.auth.user.details.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * HTTP-Security configuration class. Pre- and Post-authorization is enabled because we decided to do the API-Securing on method level in the different
 * controllers and not in this class.
 * @since 1.0
 * @author Andreas Bachl
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var unauthorizedHandler: JwtAuthEntryPoint

    @Autowired
    private lateinit var authTokenFilter: JwtAuthTokenFilter

    /**
     * Telling the authentication manager builder that we want to use the [BCryptPasswordEncoder] as our password encoder/decoder.
     * @since 1.0
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService<UserDetailsService?>(userDetailsService)
            .passwordEncoder(BCryptPasswordEncoder(8))
    }

    /**
     * Creating the authentication manager bean for spring security
     * @since 1.0
     * @throws Exception
     */
    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    /**
     * Configuring our general security method. We've disabled cors and crsf for all API-endpoints, defined the sessionManagement as Stateless (Do not exactly know if this is necessary when using JWT).
     * We also defined that all endpoints are accessible by everybody until further notice. (-> in the RestController)
     * To finish, we added our [JwtAuthTokenFilter] to the filterchain.
     * @since 1.0
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//            .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//            .antMatchers("/api/test/**").permitAll()
            .authorizeRequests().anyRequest().permitAll()
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}