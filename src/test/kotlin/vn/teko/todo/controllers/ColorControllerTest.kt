package vn.teko.todo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import vn.teko.todo.services.ColorService


@ExtendWith(SpringExtension::class)
@WebMvcTest(ColorController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class ColorControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @MockkBean
    lateinit var colorService: ColorService
    val baseUrl = "/api/colors"

    @Test
    fun testgetcolors() {
        val color1 = ColorDto(
            id = 1,
            name = "red",
            code = "111",
            isDefault = false,
        )
        val color2 = ColorDto(
            id = 2,
            name = "orange",
            code = "222",
            isDefault = false,
        )
        val color3 = ColorDto(
            id = 3,
            name = "yellow",
            code = "333",
            isDefault = true,
        )
        val color4 = ColorDto(
            id = 4,
            name = "blue",
            code = "444",
            isDefault = false,
        )
        val color5 = ColorDto(
            id = 5,
            name = "green",
            code = "333",
            isDefault = false,
        )


        val colors = listOf<ColorDto>(color1, color2, color3, color4, color5)

        val objectMapper = ObjectMapper()
        val userInfoJSON = objectMapper.writeValueAsString(colors)
        every { colorService.getColors().map { it.toColorDto() } } returns colors

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl).content(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(userInfoJSON))
            .andReturn()

        verify { colorService.getColors().map { it.toColorDto() } }
    }

}