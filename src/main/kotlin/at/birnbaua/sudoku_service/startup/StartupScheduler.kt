package at.birnbaua.sudoku_service.startup

import at.birnbaua.sudoku_service.jpa.Difficulty
import at.birnbaua.sudoku_service.jpa.DifficultyService
import at.birnbaua.sudoku_service.jpa.Sudoku
import at.birnbaua.sudoku_service.jpa.SudokuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class StartupScheduler {

    @Autowired
    lateinit var ds: DifficultyService

    @Autowired
    lateinit var ss: SudokuService

    @Scheduled(initialDelay = 500, fixedRate = 1000*60*999)
    fun startup() {
        val difficulty = Difficulty()
        difficulty.no = 1
        difficulty.name = "easy"
        difficulty.desc = "The lowest difficulty"
        ds.save(difficulty)

        val sudoku = Sudoku()
        sudoku.difficulty = difficulty
        sudoku.solved = "534678912" +
                "672195348" +
                "198342567" +
                "859761423" +
                "426853791" +
                "713924856" +
                "961537284" +
                "287419635" +
                "345286179"
        sudoku.unsolved = "530070000" +
                "600195000" +
                "098000060" +
                "800060003" +
                "400803001" +
                "700020006" +
                "060000280" +
                "000419005" +
                "000080079"
        sudoku.id = -1
        sudoku.desc = "This is a sample sudoku!"

        ss.save(sudoku)

    }
}