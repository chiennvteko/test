package vn.teko.todo.repositories

import vn.teko.todo.services.Color
import javax.persistence.*

@Entity
@Table(name = "colors")
data class ColorModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val code: String,
    @Column(name = "is_default")
    var isDefault: Boolean = false,
)

fun ColorModel.toColor(): Color = Color(
    id = this.id,
    name = this.name,
    code = this.code,
    isDefault = this.isDefault,
)

fun Color.toColorModel(): ColorModel = ColorModel(
    id = this.id,
    name = this.name,
    code = this.code,
    isDefault = this.isDefault,
)
