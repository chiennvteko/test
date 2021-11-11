package vn.teko.todo.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import javax.persistence.PersistenceContext

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class NoteRepositoryTest {

    @Autowired
    private lateinit var noteRepository: NoteRepository
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun findAll() {
        val note1 = NoteModel(
            id = 0,
            title = "title1",
            content = "conten1",
        )
        val note2 = NoteModel(
            id = 0,
            title = "title2",
        )
        val note3 = NoteModel(
            id = 0,
            title = "title3",
        )
        val note4 = NoteModel(
            id = 0,
            title = "title4",
        )
        entityManager.persist(note1)
        entityManager.persist(note2)
        entityManager.persist(note3)
        entityManager.persist(note4)
        val notes = noteRepository.findAll().map { it.toNote() }
        assertThat(notes.size).isEqualTo(4)
        assertThat(notes.get(0).title).isEqualTo("title1")
    }

    @Test
    fun findById() {
        val note1 = NoteModel(
            id = 0,
            title = "sss",
        )
        entityManager.persist(note1)
        val note = noteRepository.findById(note1.id).get()
        assertThat(note).isEqualTo(note1)
    }

    @Test
    fun save() {
        val note1 = NoteModel(
            id = 0,
            title = "sss",
        )
        entityManager.persist(note1)
        val note = noteRepository.save(note1)
        entityManager.flush()
        assertThat(note).isEqualTo(note1)
    }

    fun deleteById() {
        noteRepository.deleteById(1)
        entityManager.flush();
        assertThat(noteRepository.findById(1).isPresent).isEqualTo(false)
    }

}