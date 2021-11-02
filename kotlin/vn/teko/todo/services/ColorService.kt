package vn.teko.todo.services

interface ColorService {
    fun getColors() : List<Color>
    fun getColor(id: Long) : Color
}