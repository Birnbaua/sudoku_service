package at.birnbaua.sudoku_service.auth.config

import at.birnbaua.sudoku_service.auth.userDetails.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.jvm.Throws

@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var cuds: CustomUserDetailsService

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(cuds)
            .passwordEncoder(MyEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()

        http.authorizeRequests()
            .anyRequest().permitAll()
            .and().httpBasic()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and().rememberMe()
            .and().formLogin().loginPage("/login").defaultSuccessUrl("/admin")
            .and()
            .logout()
            .permitAll()
    }
}