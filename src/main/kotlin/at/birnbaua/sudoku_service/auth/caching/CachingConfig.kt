package at.birnbaua.sudoku_service.auth.caching

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * In-Memory cache for showing cache functionality and reduce complexity (e.g. when using redis as cache)
 * @since 1.0
 * @author Andreas Bachl
 */
@Configuration
@EnableCaching
class CachingConfig {

    /**
     * Define all separate caches
     * @since 1.0
     * @author Andreas Bachl
     * @return CacheManager for spring boot config
     */
    @Bean
    fun cacheManager() : CacheManager {
        return ConcurrentMapCacheManager("users","userDetails","roles","privileges","solved")
    }
}