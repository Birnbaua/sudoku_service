package at.birnbaua.sudoku_service.auth.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.*

/**
 * New properties for handling the JWT parameter needed.
 * @since 1.0
 * @author Andreas Bachl
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProperties{
    var prefix: String = "Bearer "
    var header: String = "authorization"
    var expiryTime: Long = 1000*60*60*24*15 // 15 days
    var secret: String = UUID.randomUUID().toString()
}