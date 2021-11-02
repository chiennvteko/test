package vn.teko.todo.services

import javax.persistence.*

data class Color(
    val id: Long,
    val name: String,
    val code: String,
    var isDefault: Boolean = false,
)
