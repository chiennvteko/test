package vn.teko.todo.repositories

import vn.teko.todo.services.Color
import vn.teko.todo.services.Note
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "notes")
data class NoteModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val title: String,
    val content: String,
    @Column(name = "create_at")
    val createAt: LocalDateTime,
    @Column(name = "edited_at")
    var editedAt: LocalDateTime,
)

fun NoteModel.toNote(colorId: Long, color: Color): Note = Note(
    id = this.id,
    title = this.title,
    content = this.content,
    colorId = colorId,
    color = color,
    createAt = this.createAt,
    editedAt = this.editedAt,
)

fun Note.toNoteModel(): NoteModel = NoteModel(
    id = this.id,
    title = this.title,
    content = this.content,
    createAt = this.createAt,
    editedAt = this.editedAt,
)