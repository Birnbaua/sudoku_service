package at.birnbaua.sudoku_service.jpa.repository

import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DifficultyRepository : JpaRepository<Difficulty, Int>