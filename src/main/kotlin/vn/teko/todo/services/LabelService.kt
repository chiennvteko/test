package vn.teko.todo.services

interface LabelService {
    fun getLabels() : List<Label>
    fun getLabel(id: Long) : Label
    fun createLabel(label: Label) : Label
    fun updateLabel(id: Long, newLabel: Label) : Label
    fun deleteLabel(id: Long) : Label
}

