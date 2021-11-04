package vn.teko.todo.repositories

import javax.persistence.*

@Entity
@Table(name = "note_labels")
data class NoteLabelModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "note_id")
    val noteId: Long,
    @Column(name = "label_id")
    val labelId: Long,
)