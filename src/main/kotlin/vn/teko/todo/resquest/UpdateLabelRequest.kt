package vn.teko.todo.resquest

import vn.teko.todo.services.Label
import javax.validation.constraints.NotBlank

data class UpdateLabelRequest(
    val id: Long,
    @NotBlank
    val name: String,
)

fun UpdateLabelRequest.toLabel() = Label(
    id = this.id,
    name = this.name,
)