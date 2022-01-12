package at.birnbaua.sudoku_service.auth.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProperties{
    var prefix: String = "Bearer "
    var header: String = "authorization"
    var expiryTime: Long = 1000*60*15 // 15 minutes
    var secret: String = UUID.randomUUID().toString()
}