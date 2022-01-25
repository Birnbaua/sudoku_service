package at.birnbaua.sudoku_service.spring

import at.birnbaua.sudoku_service.auth.user.jpa.entity.User
import at.birnbaua.sudoku_service.auth.user.jpa.service.UserService
import at.birnbaua.sudoku_service.jpa.entity.sudoku.Sudoku
import at.birnbaua.sudoku_service.spring.config.SpringSecurityMVCTestConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
//@WebMvcTest(SudokuController::class)
@AutoConfigureMockMvc
@SpringBootTest(classes = [SpringSecurityMVCTestConfig::class])
class UserAuthControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var us: UserService

    @Test
    fun testCreationOfNewUser() {
        val user = User("chrisi","Big boy Christoph","longdong","long@dong.at","Christoph","Großauer",null)
        mvc.perform(MockMvcRequestBuilders.post("/user").content(ObjectMapper().writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated)
        mvc.perform(
            MockMvcRequestBuilders.get("/user/chrisi")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath<Sudoku>("nickname", Matchers.`is`("Big boy Chrisi")))
    }

}