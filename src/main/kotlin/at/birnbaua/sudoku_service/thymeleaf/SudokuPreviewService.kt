package at.birnbaua.sudoku_service.thymeleaf

import at.birnbaua.sudoku_service.jpa.sudoku.validation.SudokuValidation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.awt.Graphics
import java.awt.GraphicsEnvironment
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import javax.swing.JEditorPane


@Service
class SudokuPreviewService {

    @Autowired
    lateinit var templateEngine: SpringTemplateEngine

    @Throws(RuntimeException::class)
    fun parseThymeleafTemplate(sudoku: String) : String {
        val arr = SudokuValidation.to2DArray(sudoku)
        val context = Context()
        templateEngine.clearTemplateCache()
        context.setVariable("sudoku",arr)
        return templateEngine.process("sudoku.html", context)
    }

    fun toImage(html: String) : ByteArray {
        val width = 250
        val height = 250
        System.setProperty("java.awt.headless", "false")
        val image = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .defaultScreenDevice.defaultConfiguration
            .createCompatibleImage(width, height)

        val graphics: Graphics = image.createGraphics()

        val jep = JEditorPane("text/html", html)
        jep.setSize(width, height)
        jep.print(graphics)
        val bos = ByteArrayOutputStream()
        ImageIO.write(image,"png",bos)
        return bos.toByteArray()
    }
}