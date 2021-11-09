package vn.teko.todo.controllers

import vn.teko.todo.services.Color
import vn.teko.todo.services.ColorService
import vn.teko.todo.services.Label
import vn.teko.todo.services.Note
import java.time.LocalDateTime

data class NoteDto(
    val id: Long,
    var title: String,
    var content: String,
    val color: Color?,
    val label: List<Label> = listOf(),
    val createAt: LocalDateTime = LocalDateTime.now(),
    var editedAt: LocalDateTime = LocalDateTime.now(),
)
fun Note.toNoteDto() = NoteDto(
        id = this.id,
        title = this.title,
        content = this.content,
        color = this.color,
        label= this.labels,
        createAt = this.createAt,
        editedAt = this.editedAt,
)
