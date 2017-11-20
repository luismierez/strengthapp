package luismierez.strenghstandards

import org.junit.Test
import org.junit.Assert.*

/**
 * Created by luismierez on 11/14/17.
 */
class FormulaTests {

    @Test
    fun epley_isCorrect() {
        val epley = Formula.Epley()
        assertEquals(215, epley.resolve(185, 5).toInt())
    }

    @Test
    fun brzycki_isCorrect() {
        val brzycki = Formula.Brzycki()

        assertEquals(208, brzycki.resolve(185, 5).toInt())
    }

    @Test
    fun mcGlothin_isCorrect() {
        val mcGlothin = Formula.McGlothin()

        assertEquals(210, mcGlothin.resolve(185, 5).toInt())
    }

    @Test
    fun lombardi_isCorrect() {
        val lombardi = Formula.Lombardi()

        assertEquals(217, lombardi.resolve(185, 5).toInt())
    }

    @Test
    fun mayhew_isCorrect() {
        val mayhew = Formula.Mayhew()

        assertEquals(220, mayhew.resolve(185, 5).toInt())
    }
}