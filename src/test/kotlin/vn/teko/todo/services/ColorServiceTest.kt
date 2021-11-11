package vn.teko.todo.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.BDDMockito.given
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import vn.teko.todo.repositories.ColorModel
import vn.teko.todo.repositories.ColorRepository
import vn.teko.todo.repositories.toColor
import java.util.*


@ExtendWith(SpringExtension::class)
internal class ColorServiceTest {

    lateinit var colorService: ColorService

    @MockBean
    private lateinit var colorRepository: ColorRepository

    @BeforeEach
    fun setup() {
        colorService = ColorServiceImpl(colorRepository)
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
        given(colorRepository.findAll()).willReturn(colorModels)
        given(colorRepository.findById(1)).willReturn(Optional.of(color1))
        given(colorRepository.findById(2)).willReturn(Optional.of(color2))
        given(colorRepository.findById(3)).willReturn(Optional.of(color3))
    }

    @Test
    fun getColors() {
        val colors = colorService.getColors()
        assertThat(colors).isEqualTo(colorRepository.findAll().map { it.toColor() })
    }

    @Test
    fun getColor() {
        var color = colorService.getColor(1)
        assertThat(color.id).isEqualTo(1)
        assertThat(color.name).isEqualTo("red")
        assertThat(colorService.getColor(3).code).isEqualTo("333")
    }
}