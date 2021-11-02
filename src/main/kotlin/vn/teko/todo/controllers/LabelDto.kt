package vn.teko.todo.controllers

import vn.teko.todo.services.Label

data class LabelDto(
    var id: Long,
    var name: String?
)

fun Label.toLabelDto() : LabelDto = LabelDto(
    id = this.id,
    name = this.name,

)
