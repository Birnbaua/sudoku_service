package at.birnbaua.sudoku_service.spring.config

import at.birnbaua.sudoku_service.auth.user.details.CustomUserDetailsService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@TestConfiguration
class SpringSecurityMVCTestConfig {


    @Bean
    @Primary
    fun userDetailsService(): UserDetailsService {
        return CustomUserDetailsService()
    }
}