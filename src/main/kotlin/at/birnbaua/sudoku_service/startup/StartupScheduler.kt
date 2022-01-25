package at.birnbaua.sudoku_service.startup

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.jpa.entity.gamestats.GameStats
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
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
            easy.min = 40
            easy.max = 45
            ds.save(easy)
        }

        if(ds.existsById(2).not()) {
            val moderate = Difficulty()
            moderate.no = 2
            moderate.name = "moderate"
            moderate.desc = "For guys who don't like challenges"
            moderate.min = 35
            moderate.max = 39
            ds.save(moderate)
        }

        if(ds.existsById(3).not()) {
            val challenging = Difficulty()
            challenging.no = 3
            challenging.name = "challenging"
            challenging.desc = "The lowest difficulty"
            challenging.min = 30
            challenging.max = 34
            ds.save(challenging)
        }

        if(ds.existsById(4).not()) {
            val hard = Difficulty()
            hard.no = 4
            hard.name = "hard"
            hard.desc = "The hard sudoku"
            hard.min = 25
            hard.max = 29
            ds.save(hard)
        }

        if(ds.existsById(5).not()) {
            val impossible = Difficulty()
            impossible.no = 5
            impossible.name = "impossible"
            impossible.desc = "Impossible sudokus"
            impossible.min = 23
            impossible.max = 24
            ds.save(impossible)
        }

        val sudoku = Sudoku()
        sudoku.type = SudokuType.NORMAL
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
        sudoku.desc = "This is a sample sudoku!"
        sudoku.owner = User("admin")

        ss.save(sudoku)

        val stats = GameStats()
        stats.user = us.findUserByUsername("admin")
        stats.sudoku = sudoku
        stats.currentResult = sudoku.unsolved.toString()
        stats.duration = Time.valueOf("00:25:12")
        stats.finished = false
        stats.preview = sps.toImage(sps.parseThymeleafTemplate(stats.currentResult!!))

        gss.saveInfo(stats)



        val diagonalSudoku = Sudoku()
        diagonalSudoku.type = SudokuType.DIAGONAL
        diagonalSudoku.owner = us.findUserByUsername("admin")
        diagonalSudoku.unsolved = "639841275" +
                "724953168" +
                "185726394" +
                "256137489" +
                "491582637" +
                "873469521" +
                "542398716" +
                "318675942" +
                "967214853"
        diagonalSudoku.preview = sps.toImage(sps.parseThymeleafTemplate(diagonalSudoku.unsolved!!))
        diagonalSudoku.difficulty = Difficulty(3)
        diagonalSudoku.desc = "I am a Diagonal Sudoku lol"
        ss.save(diagonalSudoku)

        val samuraiSudoku = Sudoku()
        samuraiSudoku.type = SudokuType.SAMURAI
        samuraiSudoku.owner = us.findUserByUsername("admin")
        samuraiSudoku.unsolved = "00000"

        samuraiString().forEachIndexed{index, x ->
            val part = Sudoku()
            part.type = samuraiSudoku.type
            part.owner = samuraiSudoku.owner
            part.unsolved = x
            samuraiSudoku.desc = samuraiSudoku.desc + ss.save(part).id
            if(samuraiString().size -1 > index) samuraiSudoku.desc = samuraiSudoku.desc + ";"
        }
        ss.save(samuraiSudoku)
    }

    private fun samuraiString() : Array<String> {
        val sudoku1 = "924538617" +
                "358617924" +
                "716492853" +
                "635789241" +
                "297146538" +
                "481325796" +
                "873254169" +
                "162973485" +
                "549861372"
        val sudoku2 = "731246895" +
                "259813467" +
                "684975312" +
                "398524176" +
                "126739584" +
                "547168239" +
                "472681953" +
                "913457628" +
                "865392741"
        val sudoku3 = "169538472" +
                "485627913" +
                "372941865" +
                "654719328" +
                "218364759" +
                "793852641" +
                "547293186" +
                "921486537" +
                "836175294"
        val sudoku4 = "189326547" +
                "346587921" +
                "527914836" +
                "238659174" +
                "765841293" +
                "491273658" +
                "612435789" +
                "873192465" +
                "954768312"
        val sudoku5 = "186495732" +
                "537281694" +
                "294763581" +
                "453819267" +
                "628537419" +
                "719642853" +
                "861374925" +
                "342956178" +
                "975128346"
        return arrayOf(sudoku1,sudoku2,sudoku3,sudoku4,sudoku5)
    }
}