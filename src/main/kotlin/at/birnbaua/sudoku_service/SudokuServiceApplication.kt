package at.birnbaua.sudoku_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class SudokuServiceApplication

fun main(args: Array<String>) {
    runApplication<SudokuServiceApplication>(*args)
}
