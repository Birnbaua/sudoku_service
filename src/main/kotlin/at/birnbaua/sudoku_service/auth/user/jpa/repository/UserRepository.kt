package at.birnbaua.sudoku_service.auth.user.jpa.repository

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.projection.user.PrivateUserInfo
import at.birnbaua.sudoku_service.auth.user.jpa.projection.user.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User, String> {

    fun findUserByUsername(username: String) : Optional<User>

    @Query("SELECT u FROM User u WHERE u.username = :username")
    fun findPrivateUserInfo(username: String) : Optional<PrivateUserInfo>

    @Query("SELECT u FROM User u WHERE u.username = :username")
    fun findUserInfo(username: String) : Optional<UserInfo>
}