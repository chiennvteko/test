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
    fun getColors(): ResponseEntity<List<ColorDto>> {
        val colorDtos = colorService.getColors().map { it.toColorDto() }
        return ResponseEntity.ok(colorDtos)
    }

}