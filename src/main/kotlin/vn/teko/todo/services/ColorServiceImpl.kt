package vn.teko.todo.services

import org.springframework.stereotype.Service
import vn.teko.todo.exception.NotFoundException
import vn.teko.todo.repositories.*

@Service
class ColorServiceImpl(
    private val colorRepository: ColorRepository,
    private val noteRepository: NoteRepository,
) : ColorService {

    override fun getColors(): List<Color> {
        val colors = colorRepository.findAll().map { it.toColor() }
        return colors
    }

    override fun getColor(id: Long): Color {
        val optionalColorModel = colorRepository.findById(id).orElseThrow { NotFoundException(message = "not found colorid = $id ") }
        return optionalColorModel.toColor()
    }

}