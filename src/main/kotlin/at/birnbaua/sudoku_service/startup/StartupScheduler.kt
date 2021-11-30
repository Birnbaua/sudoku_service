package at.birnbaua.sudoku_service.startup

import at.birnbaua.sudoku_service.auth.config.MyEncoder
import at.birnbaua.sudoku_service.jpa.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.gamestats.GameStatsService
import at.birnbaua.sudoku_service.jpa.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.sudoku.DifficultyService
import at.birnbaua.sudoku_service.jpa.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.sudoku.SudokuService
import at.birnbaua.sudoku_service.jpa.sudoku.validation.SudokuValidation
import at.birnbaua.sudoku_service.jpa.user.Role
import at.birnbaua.sudoku_service.jpa.user.RoleService
import at.birnbaua.sudoku_service.jpa.user.User
import at.birnbaua.sudoku_service.jpa.user.UserService
import at.birnbaua.sudoku_service.thymeleaf.SudokuPreviewService
import org.springframework.beans.factory.annotation.Autowired
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
    lateinit var vs: SudokuValidation

    @Autowired
    lateinit var rs: RoleService

    @Autowired
    lateinit var us: UserService

    @Autowired
    lateinit var gss: GameStatsService

    @Autowired
    lateinit var sps: SudokuPreviewService

    @Scheduled(initialDelay = 500, fixedRate = 1000*60*999)
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

        val adminRole = Role("ADMIN")
        val guestRole = Role("GUEST")

        rs.saveAndFlush(adminRole)
        rs.saveAndFlush(guestRole)

        val admin = User()
        admin.username = "admin"
        admin.firstName = "Max"
        admin.lastName = "Mustermann"
        admin.roles.add(adminRole)
        admin.roles.add(guestRole)
        admin.password = MyEncoder().encode("admin")
        us.save(admin)

        val stats = GameStats()
        stats.user = admin
        stats.sudoku = sudoku
        stats.currentResult = sudoku.unsolved.toString()
        stats.duration = Time.valueOf("00:25:12")
        stats.isFinished = false
        stats.preview = sps.toImage(sps.parseThymeleafTemplate(stats.currentResult!!))

        gss.saveInfo(stats)

    }
}