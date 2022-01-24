package at.birnbaua.sudoku_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication
class SudokuServiceApplication

fun main(args: Array<String>) {
    runApplication<SudokuServiceApplication>(*args)
}
