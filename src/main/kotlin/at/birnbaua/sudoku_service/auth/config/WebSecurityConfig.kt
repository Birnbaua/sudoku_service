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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var unauthorizedHandler: JwtAuthEntryPoint

    @Autowired
    lateinit var authTokenFilter: JwtAuthTokenFilter

    @Throws(Exception::class)
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService<UserDetailsService?>(userDetailsService)
            .passwordEncoder(BCryptPasswordEncoder(8))
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

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