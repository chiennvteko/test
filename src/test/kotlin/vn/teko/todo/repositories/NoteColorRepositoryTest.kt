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
internal class NoteColorRepositoryTest {

    @Autowired
    private lateinit var noteColorRepository: NoteColorRepository
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun findAll() {
        val noteColor1 = NoteColorModel(
            id = 0,
            noteId = 1,
            colorId = 1,
        )
        val noteColor2 = NoteColorModel(
            id = 0,
            noteId = 2,
            colorId = 1,
        )

        entityManager.persist(noteColor1)
        entityManager.persist(noteColor2)
        val noteColors = noteColorRepository.findAll()as List<NoteColorModel>
        assertThat(noteColors.size).isEqualTo(2)
    }

    @Test
    fun findById() {
        val noteColor1 = NoteColorModel(
            id = 0,
            noteId = 2,
            colorId = 2,
        )
        entityManager.persist(noteColor1)
        val noteColor = noteColorRepository.findById(noteColor1.id).get()
        assertThat(noteColor).isEqualTo(noteColor1)
    }

    @Test
    fun save() {
        val noteColor1 = NoteColorModel(
            id = 0,
            noteId = 1,
            colorId = 1,
        )
        entityManager.persist(noteColor1)
        val noteColor = noteColorRepository.save(noteColor1)
        entityManager.flush()
        assertThat(noteColor).isEqualTo(noteColor1)
    }

    @Test
    fun deleteById() {
        val noteColor1 = NoteColorModel(
            id = 0,
            noteId = 1,
            colorId = 1,
        )
        entityManager.persist(noteColor1)
        val noteColor = noteColorRepository.save(noteColor1)
        noteColorRepository.deleteById(noteColor.id)
        entityManager.flush();
        assertThat(noteColorRepository.findById(noteColor.id).isPresent).isEqualTo(false)
    }

    @Test
    fun getNoteColor() {
        val noteColor1 = NoteColorModel(
            id = 0,
            noteId = 2,
            colorId = 1,
        )
        entityManager.persist(noteColor1)
        noteColorRepository.save(noteColor1)
        assertThat(noteColorRepository.getNoteColor(2)).isEqualTo(1)

    }

    @Test
    fun deleteByNoteId() {
        val noteColor1 = NoteColorModel(
            id = 0,
            noteId = 3,
            colorId = 1,
        )
        val noteColor = noteColorRepository.save(noteColor1)
        noteColorRepository.deleteByNoteId(noteColor.noteId)
        val noteColors = noteColorRepository.findAll() as List<NoteColorModel>
        assertThat(noteColors.size).isEqualTo(0)

    }

}