package at.birnbaua.sudoku_service.auth.user.jpa.service

import at.birnbaua.sudoku_service.auth.exception.RoleNotFoundException
import at.birnbaua.sudoku_service.auth.user.jpa.entity.Role
import at.birnbaua.sudoku_service.auth.user.jpa.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class RoleService {

    @Autowired
    private lateinit var rr: RoleRepository

    @CachePut(value = ["roles"], key = "#role.name")
    fun save(role: Role) : Role {
        return rr.save(role)
    }

    @Cacheable(value = ["roles"], key = "id")
    fun findById(id: String) : Role {
        return rr.findById(id).orElseThrow { RoleNotFoundException(id) }
    }

    @CacheEvict(value = ["roles"], key = "id")
    fun deleteById(id: String) {
        return rr.deleteById(id)
    }
}