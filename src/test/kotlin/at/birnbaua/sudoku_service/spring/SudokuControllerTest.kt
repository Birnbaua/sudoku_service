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
import at.birnbaua.sudoku_service.jpa.projection.SudokuInfo
import at.birnbaua.sudoku_service.spring.config.SpringSecurityMVCTestConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.jayway.jsonpath.JsonPath
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


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

    @BeforeEach
    fun before() {
        ds.save(Difficulty(1,"easy","description for 1",39,47))
        rs.save(Role("ADMIN"))
        val roles = mutableSetOf(Role("ADMIN"))
        us.insert(User("admin","max","admin","andre@gmx.at","max","muster",null, roles))
        us.insert(User("guest","max","guest","andre@gmx.at","max","muster",null))
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

}