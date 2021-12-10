package at.birnbaua.sudoku_service.auth.user.jpa.service

import at.birnbaua.sudoku_service.auth.exception.RoleNotFoundException
import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege
import at.birnbaua.sudoku_service.auth.user.jpa.repository.PrivilegeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@Suppress("unused")
class PrivilegeService {

    @Autowired
    private lateinit var pr: PrivilegeRepository

    @CachePut(value = ["privileges"], key = "#privilege.name")
    fun save(privilege: Privilege) : Privilege {
        return pr.save(privilege)
    }

    @Cacheable(value = ["privileges"], key = "id")
    fun findById(id: String) : Privilege {
        return pr.findById(id).orElseThrow { RoleNotFoundException(id) }
    }

    @CacheEvict(value = ["privileges"], key = "id")
    fun deleteById(id: String) {
        return pr.deleteById(id)
    }

    fun findAll(): MutableList<Privilege> {
        return pr.findAll()
    }
}