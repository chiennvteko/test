package vn.teko.todo.repositories

import javax.persistence.*

@Entity
@Table(name = "note_colors")
data class NoteColorModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "note_id")
    val noteId: Long,
    @Column(name = "color_id")
    val colorId: Long,
)