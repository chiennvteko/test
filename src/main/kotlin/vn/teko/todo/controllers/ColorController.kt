package vn.teko.todo.controllers

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import vn.teko.todo.services.ColorService

@RestController
@RequestMapping("/api/colors")
class ColorController(
    private val colorService: ColorService,
) {
    @GetMapping
    fun getColors(): List<ColorDto> {
        val colorDtos = colorService.getColors().map { it.toColorDto() }
        return colorDtos
    }

    @GetMapping("/{id}")
    fun getColor(
        @PathVariable id: Long,
        ): ColorDto {
        return colorService.getColor(id).toColorDto()
    }
}