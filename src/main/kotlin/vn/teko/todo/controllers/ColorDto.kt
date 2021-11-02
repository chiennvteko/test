package vn.teko.todo.controllers

import vn.teko.todo.repositories.ColorModel
import vn.teko.todo.services.Color

data class ColorDto(
    val id: Long,
    val name: String,
    val code: String,
    var isDefault: Boolean = false,
)

fun ColorDto.toColor(): Color = Color(
    id = this.id,
    name = this.name,
    code = this.code,
    isDefault = this.isDefault,
)


fun Color.toColorDto(): ColorDto = ColorDto(
    id = this.id,
    name = this.name,
    code = this.code,
    isDefault = this.isDefault,
)
