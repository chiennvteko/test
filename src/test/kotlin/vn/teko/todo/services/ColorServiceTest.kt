package vn.teko.todo.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import vn.teko.todo.exception.NotFoundException
import vn.teko.todo.repositories.ColorModel
import vn.teko.todo.repositories.ColorRepository
import vn.teko.todo.repositories.toColor
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class ColorServiceTest {

    lateinit var colorService: ColorService

    @Mock
    private lateinit var colorRepository: ColorRepository

    @BeforeEach
    fun setup() {
        colorService = ColorServiceImpl(colorRepository)
    }

    @Test
    fun getColors() {
        val color1 = ColorModel(
            id = 1,
            name = "red",
            code = "111",
            isDefault = false
        )
        val color2 = ColorModel(
            id = 2,
            name = "orange",
            code = "222",
            isDefault = false
        )
        val color3 = ColorModel(
            id = 3,
            name = "yellow",
            code = "333",
            isDefault = true
        )
        val colorModels = listOf<ColorModel>(color1, color2, color3)
        Mockito.`when`(colorRepository.findAll()).thenReturn(colorModels)
        val colors = colorService.getColors()
        assertThat(colors).isEqualTo(colorRepository.findAll().map { it.toColor() })
    }

    @Test
    fun getColor() {
        val color1 = ColorModel(
            id = 1,
            name = "red",
            code = "111",
            isDefault = false
        )
        val color2 = ColorModel(
            id = 2,
            name = "orange",
            code = "222",
            isDefault = false
        )
        val color3 = ColorModel(
            id = 3,
            name = "yellow",
            code = "333",
            isDefault = true
        )
        val colorModels = listOf<ColorModel>(color1, color2, color3)
        Mockito.`when`(colorRepository.findById(1)).thenReturn(Optional.of(color1))
        var color = colorService.getColor(1)
        assertThat(color).isEqualTo(color1.toColor())
        Mockito.`when`(colorRepository.findById(2)).thenReturn(Optional.of(color2))
        color = colorService.getColor(2)
        assertThat(color).isEqualTo(color2.toColor())

    }
}