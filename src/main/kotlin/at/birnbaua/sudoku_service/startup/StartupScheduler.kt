package at.birnbaua.sudoku_service.startup

import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.jpaservice.DifficultyService
import at.birnbaua.sudoku_service.jpa.jpaservice.GameStatsService
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import at.birnbaua.sudoku_service.thymeleaf.SudokuPreviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.sql.Time

@Configuration
@EnableScheduling
class StartupScheduler {

    @Autowired
    lateinit var ds: DifficultyService

    @Autowired
    lateinit var ss: SudokuService

    @Autowired
    lateinit var us: UserService

    @Autowired
    lateinit var gss: GameStatsService

    @Autowired
    lateinit var sps: SudokuPreviewService

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Scheduled(initialDelay = 2000, fixedRate = 1000*60)
    fun sudokuCache() {
        cacheManager.getCache("solved")?.clear()
    }

    @Scheduled(initialDelay = 2000, fixedRate = 1000*60*999)
    fun startup() {
        if(ds.existsById(1).not()) {
            val easy = Difficulty()
            easy.no = 1
            easy.name = "easy"
            easy.desc = "The lowest difficulty"
            ds.save(easy)
        }

        if(ds.existsById(2).not()) {
            val moderate = Difficulty()
            moderate.no = 2
            moderate.name = "moderate"
            moderate.desc = "For guys who don't like challenges"
            ds.save(moderate)
        }

        if(ds.existsById(3).not()) {
            val challenging = Difficulty()
            challenging.no = 3
            challenging.name = "challenging"
            challenging.desc = "The lowest difficulty"
            ds.save(challenging)
        }

        if(ds.existsById(4).not()) {
            val hard = Difficulty()
            hard.no = 4
            hard.name = "hard"
            hard.desc = "The hard sudoku"
            ds.save(hard)
        }

        if(ds.existsById(5).not()) {
            val impossible = Difficulty()
            impossible.no = 5
            impossible.name = "impossible"
            impossible.desc = "Impossible sudokus"
            ds.save(impossible)
        }

        val sudoku = Sudoku()
        sudoku.difficulty = Difficulty(1)
        sudoku.unsolved = "530070000" +
                "600195000" +
                "098000060" +
                "800060003" +
                "400803001" +
                "700020006" +
                "060000280" +
                "000419005" +
                "000080079"
        sudoku.id = 2
        sudoku.desc = "This is a sample sudoku!"

        ss.save(sudoku)

        val stats = GameStats()
        stats.user = us.findUserByUsername("admin")
        stats.sudoku = sudoku
        stats.currentResult = sudoku.unsolved.toString()
        stats.duration = Time.valueOf("00:25:12")
        stats.isFinished = false
        stats.preview = sps.toImage(sps.parseThymeleafTemplate(stats.currentResult!!))

        gss.saveInfo(stats)

    }
}