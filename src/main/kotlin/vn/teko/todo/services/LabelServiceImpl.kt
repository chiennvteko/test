package vn.teko.todo.services

import org.springframework.stereotype.Service
import vn.teko.todo.exception.NotFoundException
import vn.teko.todo.repositories.LabelRepository
import vn.teko.todo.repositories.toLabel
import vn.teko.todo.repositories.toLabelModel

@Service
class LabelServiceImpl(
    private var labelRepositories: LabelRepository,
) : LabelService {

    override fun getLabels(): List<Label> {
        val labels = labelRepositories.findAll().map { it.toLabel() }
        return labels
    }

    override fun getLabel(id: Long): Label {
        val optionalLabelModel = labelRepositories.findById(id).orElseThrow { NotFoundException(message = "not found labelid = $id ") }
        return optionalLabelModel.toLabel()
    }

    override fun createLabel(label: Label): Label {
        return labelRepositories.save(label.toLabelModel()).toLabel()
    }

    override fun updateLabel(id: Long, newLabel: Label): Label {
        val optionalLabelModel = labelRepositories.findById(id).orElseThrow { NotFoundException(message = "not found labelid = $id ") }
        val label = optionalLabelModel.toLabel().apply {
            name = newLabel.name
        }
        return labelRepositories.save(label.toLabelModel()).toLabel()
    }

    override fun deleteLabel(id: Long): Label {
        val optionalLabelModel = labelRepositories.findById(id).orElseThrow { NotFoundException(message = "not found labelid = $id ") }
        labelRepositories.deleteById(id)
        return optionalLabelModel.toLabel()
    }
}

