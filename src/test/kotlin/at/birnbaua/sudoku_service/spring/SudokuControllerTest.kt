package at.birnbaua.sudoku_service.spring

import at.birnbaua.sudoku_service.auth.user.jpa.entity.Role
import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.RoleService
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.controller.SudokuController
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Difficulty
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.jpa.entity.sudoku.SudokuType
import at.birnbaua.sudoku_service.jpa.jpaservice.DifficultyService
import at.birnbaua.sudoku_service.jpa.jpaservice.SudokuService
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.jpa.solver.Hint
import at.birnbaua.sudoku_service.jpa.solver.SudokuSolver
import at.birnbaua.sudoku_service.spring.config.SpringSecurityMVCTestConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.jayway.jsonpath.JsonPath
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime


@RunWith(SpringRunner::class)
//@WebMvcTest(SudokuController::class)
@AutoConfigureMockMvc
@SpringBootTest(classes = [SpringSecurityMVCTestConfig::class])
class SudokuControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var ds: DifficultyService

    @Autowired
    private lateinit var us: UserService

    @Autowired
    private lateinit var rs: RoleService

    @Autowired
    private lateinit var ss: SudokuService

    @BeforeEach
    fun before() {
        ds.save(Difficulty(1,"easy","description for 1",39,47))
        rs.save(Role("ADMIN"))
        val roles = mutableSetOf(Role("ADMIN"))
        us.insert(User("admin","max","admin","andre@gmx.at","max","muster",null, roles))
        us.insert(User("guest","maxi","guest","andreee@gmx.at","max","muster",null))
    }


    @Test
    @WithUserDetails("admin")
    fun saveNewSudokuAndGet() {

        var sudoku = Sudoku()
        sudoku.type = SudokuType.NORMAL
        sudoku.desc = "Testdesc"
        sudoku.difficulty = Difficulty(1)
        sudoku.unsolved = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        var id: Int = -1
        mvc.perform(post("/sudoku").content(ObjectMapper().writeValueAsString(sudoku))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andDo { x ->
                id = JsonPath.read<Int>(x.response.contentAsString, "id")
            }

        mvc.perform(get("/sudoku/$id")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Sudoku>("id", `is`("$id".toInt())))
            .andExpect(jsonPath<Sudoku>("unsolved", `is` (sudoku.unsolved)))
    }

    @Test
    fun saveNewSudokuFail() {

        var sudoku = Sudoku()
        sudoku.type = SudokuType.NORMAL
        sudoku.desc = "Testdesc"
        sudoku.difficulty = Difficulty(1)
        sudoku.unsolved = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        mvc.perform(post("/sudoku").content(ObjectMapper().writeValueAsString(sudoku))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithUserDetails("admin")
    fun editExistingSudoku() {
        var sudoku = Sudoku()
        sudoku.type = SudokuType.NORMAL
        val now = LocalDateTime.now().toString()
        sudoku.desc = now
        sudoku.difficulty = Difficulty(1)
        sudoku.unsolved = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        val id = ss.findAll()[0].id!!
        mvc.perform(put("/sudoku/$id").content(ObjectMapper().writeValueAsString(sudoku))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("desc",`is`(now)))
    }

    @Test
    fun getAllSudokus() {
        mvc.perform(get("/sudoku?size=6")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<MutableCollection<Sudoku>>("content", hasSize(6)))
    }

    @Test
    @WithUserDetails("admin")
    fun deleteExistingSudoku() {
        val id = ss.findAll()[ss.findAll().size-1].id!!
        mvc.perform(delete("/sudoku/$id")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted)
        assert(ss.existsById(id).not())
    }

    @Test
    fun testValidateNormal() {
        val sudoku = ss.findAllByType(SudokuType.NORMAL).content[ss.findAllByType(SudokuType.NORMAL).size-1]
        mvc.perform(get("/sudoku/${sudoku.id}/validate?solved=${SudokuSolver.solveNormal(sudoku.unsolved!!)}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Boolean>("\$",`is`(true)))
    }

    @Test
    fun testValidateIncompleteNormal() {
        val sudoku = ss.findAllByType(SudokuType.NORMAL).content[ss.findAllByType(SudokuType.NORMAL).size-1]
        mvc.perform(get("/sudoku/${sudoku.id}/validate?solved=${sudoku.unsolved!!}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Boolean>("\$",`is`(false)))
    }

    @Test
    fun testValidateDiagonal() {
        val sudoku = "639841275" +
                "724953168" +
                "185726394" +
                "256137489" +
                "491582637" +
                "873469521" +
                "542398716" +
                "318675942" +
                "967214853"
        val normalSudoku = "539876412" +
                "728314965" +
                "641295738" +
                "462539871" +
                "385721649" +
                "197468253" +
                "256187394" +
                "913642587" +
                "874953126"
        mvc.perform(get("/sudoku/1/validate?type=DIAGONAL&solved=$sudoku")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Boolean>("\$",`is`(true)))
        mvc.perform(get("/sudoku/1/validate?type=DIAGONAL&solved=$normalSudoku")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Boolean>("\$",`is`(false)))
    }

    @Test
    fun testValidateSamurai() {
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
        val sudoku5 = "186495732537281694294763581453819267628537419719642853861374925342956178975128346"
        val sudoku = "$sudoku1;$sudoku2;$sudoku3;$sudoku4;$sudoku5"
        mvc.perform(get("/sudoku/1/validate?type=SAMURAI&solved=$sudoku")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$",`is`(true)))
    }

    @Test
    fun testHint() {
        val unsolved = "509000400" +
                "708304900" +
                "601000730" +
                "462500000" +
                "385720649" +
                "107408200" +
                "200100004" +
                "003040087" +
                "070053006"
        var hint: Hint = Hint(0,0,0)
        mvc.perform(get("/sudoku/1/hint?sudoku=$unsolved")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo { x -> hint = Hint(JsonPath.read(x.response.contentAsString, "row"),
                JsonPath.read<Byte>(x.response.contentAsString, "column"),
                JsonPath.read<Byte>(x.response.contentAsString, "value")) }
        val solved = SudokuSolver.solveNormal(unsolved)
        println(solved)
        println(hint.row*9+hint.column)
        println(solved[hint.row*9+hint.column].toString().toByte())
        assert(solved[hint.row*9+hint.column].toString().toByte() == hint.value)

    }


}