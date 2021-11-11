package vn.teko.todo.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import vn.teko.todo.repositories.LabelModel
import vn.teko.todo.repositories.LabelRepository
import vn.teko.todo.repositories.toLabel
import java.util.*


@ExtendWith(SpringExtension::class)
internal class LabelServiceTest {

    lateinit var labelService: LabelService

    @MockBean
    private lateinit var labelRepository: LabelRepository

    private val addLabel = LabelModel(
        id = 0,
        name = "test add label 1"
    )
    private val updateLabel = LabelModel(
        id = 1,
        name = "test update label 1"
    )

    @BeforeEach
    fun setUp() {
        labelService = LabelServiceImpl(labelRepository)
        val label1 = LabelModel(
            id = 1,
            name = "name1",
        )
        val label2 = LabelModel(
            id = 2,
            name = "name2",
        )
        val label3 = LabelModel(
            id = 3,
            name = "name3",
        )
        val label4 = LabelModel(
            id = 4,
            name = "name4",
        )
        val labels = listOf<LabelModel>(label1, label2, label3, label4)
        given(labelRepository.findAll()).willReturn(labels)
        given(labelRepository.findById(1)).willReturn(Optional.of(label1))
        given(labelRepository.findById(2)).willReturn(Optional.of(label2))
        given(labelRepository.save(addLabel)).willReturn(
            LabelModel(
            id = 5,
            name = addLabel.name
            )
        )
        given(labelRepository.save(updateLabel)).willReturn(
            LabelModel(
            id = updateLabel.id,
            name = updateLabel.name
            )
        )
        Mockito.doNothing().`when`(labelRepository).deleteById(2)
    }

    @Test
    fun getLabels() {
        assertThat(labelService.getLabels()).isEqualTo(labelRepository.findAll().map { it.toLabel() })
    }

    @Test
    fun getLabel() {
        val label = labelService.getLabel(1)
        assertThat(label).isEqualTo(labelRepository.findById(1).map { it.toLabel() }.get())
        assertThat(label.id).isEqualTo(1)
        assertThat(label.name).isEqualTo("name1")
    }

    @Test
    fun createLabel() {
        val label = labelService.createLabel(addLabel.toLabel())
        assertThat(label.id).isEqualTo(5)
        Mockito.verify(labelRepository).save(addLabel)
    }

    @Test
    fun updateLabel() {
        val label = labelService.updateLabel(1, updateLabel.toLabel())
        assertThat(label.name).isEqualTo(updateLabel.name)
        Mockito.verify(labelRepository).save(updateLabel)
    }

    @Test
    fun deleteLabel() {
        labelService.deleteLabel(2)
        Mockito.verify(labelRepository).findById(2)
        Mockito.verify(labelRepository).deleteById(2)

    }

}