package at.birnbaua.sudoku_service.auth.user.jpa.repository

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Privilege
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface PrivilegeRepository : JpaRepository<Privilege, String>