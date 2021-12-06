package at.birnbaua.sudoku_service.jpa.jpaservice

import at.birnbaua.sudoku_service.jpa.repository.DifficultyRepository
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DifficultyService @Autowired constructor(rep: DifficultyRepository) : JpaService<Difficulty, Int>(rep)