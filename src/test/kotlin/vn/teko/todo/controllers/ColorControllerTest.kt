package vn.teko.todo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import org.hamcrest.Matchers.hasSize
import jdk.nashorn.internal.objects.NativeArray.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import vn.teko.todo.services.ColorService
import vn.teko.todo.services.ColorServiceImpl

@RunWith(SpringRunner::class)
//@ExtendWith(SpringExtension::class)
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
        val colorsJSON = objectMapper.writeValueAsString(colors)

        mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andReturn()

        println("test 4")
    }



}

