package at.birnbaua.sudoku_service.controller

import at.birnbaua.sudoku_service.jpa.entity.SudokuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/sudoku")
class SudokuController {

    @Autowired
    lateinit var ss: SudokuService
}