package at.birnbaua.sudoku_service.thymeleaf

import at.birnbaua.sudoku_service.jpa.validation.SudokuValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.awt.Graphics
import java.awt.GraphicsEnvironment
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import javax.swing.JEditorPane

/**
 * This class is for generating preview pictures with the Thymeleaf template engine.
 * @since 1.0
 * @author Andreas Bachl
 */
@Service
class SudokuPreviewService {

    @Autowired
    private lateinit var templateEngine: SpringTemplateEngine

    /**
     * Parses the given sudoku with the thymeleaf template to a valid html string.
     * @since 1.0
     * @param sudoku: Sudoku with length 81
     * @return Html string with sudoku in Table
     */
    @Throws(RuntimeException::class)
    fun parseThymeleafTemplate(sudoku: String) : String {
        val arr = SudokuValidator.to2DArray(sudoku)
        val context = Context()
        templateEngine.clearTemplateCache()
        context.setVariable("sudoku",arr)
        return templateEngine.process("sudoku.html", context)
    }

    /**
     * Converts the given html to an image as ByteArray for storing in db
     * @since 1.0
     * @param html Valid html string
     * @return Preview picture as ByteArray
     */
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