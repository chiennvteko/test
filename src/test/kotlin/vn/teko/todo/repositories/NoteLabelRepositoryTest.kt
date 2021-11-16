package vn.teko.todo.repositories

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class NoteLabelRepositoryTest {

    @Autowired
    private lateinit var noteLabelRepository: NoteLabelRepository
    @Autowired
    private lateinit var labelRepository: LabelRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun findAll() {
        val noteLabel1 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 1,
        )
        val noteLabel2 = NoteLabelModel(
            id = 0,
            noteId = 2,
            labelId = 1,
        )

        entityManager.persist(noteLabel1)
        entityManager.persist(noteLabel2)
        val noteLabels = noteLabelRepository.findAll() as List<NoteLabelModel>
        assertThat(noteLabels.size).isEqualTo(2)
    }

    @Test
    fun findById() {
        val noteLabel1 = NoteLabelModel(
            id = 0,
            noteId = 2,
            labelId = 2,
        )
        entityManager.persist(noteLabel1)
        val noteLabel = noteLabelRepository.findById(noteLabel1.id).get()
        assertThat(noteLabel).isEqualTo(noteLabel1)
    }

    @Test
    fun save() {
        val noteLabel1 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 1,
        )
        entityManager.persist(noteLabel1)
        val noteLabel = noteLabelRepository.save(noteLabel1)
        entityManager.flush()
        assertThat(noteLabel).isEqualTo(noteLabel1)
    }

    @Test
    fun deleteById() {
        val noteLabel1 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 1,
        )
        entityManager.persist(noteLabel1)
        val noteLabel = noteLabelRepository.save(noteLabel1)
        noteLabelRepository.deleteById(noteLabel.id)
        entityManager.flush();
        assertThat(noteLabelRepository.findById(noteLabel.id).isPresent).isEqualTo(false)
    }

    @Test
    fun getLabelByNote() {
        val label1 = LabelModel(
            id = 0,
            name = "name1",
        )
        val label2 = LabelModel(
            id = 0,
            name = "name2",
        )
        labelRepository.save(label1)
        labelRepository.save(label2)
        val noteLabel1 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 1,
        )
        val noteLabel2 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 2,
        )
        noteLabelRepository.save(noteLabel1)
        noteLabelRepository.save(noteLabel2)
        val labels = noteLabelRepository.getLabelByNote(1)
        assertThat(labels.size).isEqualTo(2)
    }

    @Test
    fun deleteNoteLabelModelByNoteId() {
        val noteLabel1 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 1,
        )
        val noteLabel2 = NoteLabelModel(
            id = 0,
            noteId = 1,
            labelId = 2,
        )
        noteLabelRepository.save(noteLabel1)
        noteLabelRepository.save(noteLabel2)
        noteLabelRepository.deleteNoteLabelModelByNoteId(1)
        val noteLabels = noteLabelRepository.findAll() as List<NoteLabelModel>
        assertThat(noteLabels.size).isEqualTo(0)
    }


}