package vn.teko.todo.repositories

import vn.teko.todo.services.Label
import javax.persistence.*

@Entity
@Table(name = "labels")
data class LabelModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
)

fun LabelModel.toLabel() : Label = Label(
    id = this.id,
    name = this.name,
)

fun Label.toLabelModel() : LabelModel = LabelModel(
    id = this.id,
    name = this.name,
)