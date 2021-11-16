package vn.teko.todo.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import vn.teko.todo.repositories.*
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
internal class NoteServiceTest {

    lateinit var noteService: NoteService
    @MockBean
    private lateinit var noteRepository: NoteRepository
    @MockBean
    private lateinit var noteColorRepository: NoteColorRepository
    @MockBean
    private lateinit var colorRepository: ColorRepository
    @MockBean
    private lateinit var noteLabelModelRepository: NoteLabelRepository
    @MockBean
    private lateinit var labelRepository: LabelRepository

    private val label1 = Label(
        id = 1,
        name = "label 1"
    )
    private val label2 = Label(
        id = 2,
        name = "label 2"
    )
    private val color1 = Color(
        id = 1,
        name = "red",
        code = "111",
        isDefault = false
    )
    private val addRequest = Note(
        id = 0,
        title = "add note",
        content = "ssss",
        colorId = 1,
        color = color1,
        createAt = LocalDateTime.now(),
        editedAt = LocalDateTime.now(),
    )
    private val updateRequest = Note(
        id = 1,
        title = "update note",
        content = "ssss",
        colorId = 1,
        color = color1,
        createAt = LocalDateTime.now(),
        editedAt = LocalDateTime.now(),
    )


    @BeforeEach
    fun setUp() {
        noteService = NoteServiceImpl(
            noteRepository = noteRepository,
            noteColorRepository = noteColorRepository,
            colorRepository = colorRepository,
            noteLabelModelRepository = noteLabelModelRepository,
            labelRepository = labelRepository
        )
        val note1 = NoteModel(
            id = 1,
            title = "111",
            content = "note 1",
            createAt = LocalDateTime.now(),
            editedAt = LocalDateTime.now(),
        )
        val note2 = NoteModel(
            id = 2,
            title = "222",
            content = "note 2",
            createAt = LocalDateTime.now(),
            editedAt = LocalDateTime.now(),
        )
        val note3 = NoteModel(
            id = 3,
            title = "333",
            content = "note 3",
            createAt = LocalDateTime.now(),
            editedAt = LocalDateTime.now(),
        )
        val notes = listOf<NoteModel>(note1, note2, note3)
        given(noteRepository.findAll()).willReturn(notes)
        given(noteRepository.findById(1)).willReturn(Optional.of(note1))
        given(noteRepository.findById(2)).willReturn(Optional.of(note2))
        given(noteRepository.findById(3)).willReturn(Optional.of(note3))
        given(noteRepository.save(addRequest.toNoteModel())).willReturn(
            NoteModel(
                id = 4,
                title = "add note",
                content = "ssss",
                createAt = LocalDateTime.now(),
                editedAt = LocalDateTime.now(),
            )
        )
        given(noteRepository.save(updateRequest.toNoteModel())).willReturn(
            NoteModel(
                id = updateRequest.id,
                title = updateRequest.title,
                content = updateRequest.content,
                createAt = updateRequest.createAt,
                editedAt = LocalDateTime.now(),
            )
        )
        Mockito.doNothing().`when`(labelRepository).deleteById(3)

        given(colorRepository.findById(1)).willReturn(Optional.of(color1.toColorModel()))

        given(labelRepository.findById(1)).willReturn(Optional.of(label1.toLabelModel()))
        given(labelRepository.findById(2)).willReturn(Optional.of(label2.toLabelModel()))

        Mockito.doNothing().`when`(noteColorRepository).deleteByNoteId(3)

        given(noteColorRepository.getNoteColor(1)).willReturn(1)
        given(noteColorRepository.getNoteColor(2)).willReturn(1)
        given(noteColorRepository.getNoteColor(3)).willReturn(1)
        given(noteLabelModelRepository.getLabelByNote(1)).willReturn(listOf<Label>(label1, label2).map { it.toLabelModel() })
        given(noteLabelModelRepository.getLabelByNote(2)).willReturn(listOf<Label>(label1).map { it.toLabelModel() })
        given(noteLabelModelRepository.getLabelByNote(3)).willReturn(listOf<Label>(label2).map { it.toLabelModel() })
    }

    @Test
    fun getNotes() {
        val notes = noteService.getNotes()
        assertThat(notes.size).isEqualTo(3)
        assertThat(notes.get(0).labels.get(0)).isEqualTo(label1)
        assertThat(notes.get(0).labels.size).isEqualTo(2)
        assertThat(notes.get(1).labels.get(0)).isEqualTo(label1)
        assertThat(notes.get(1).labels.size).isEqualTo(1)
    }

    @Test
    fun getNote() {
        val note = noteService.getNote(1)
        assertThat(note.color).isEqualTo(color1)
        assertThat(note.id).isEqualTo(1)
        Mockito.verify(noteLabelModelRepository).getLabelByNote(1)
    }

    @Test
    fun createNote() {
        val note = noteService.createNote(addRequest)
        assertThat(note.id).isEqualTo(4)
        assertThat(note.color).isEqualTo(color1)
        assertThat(note.title).isEqualTo("add note")

    }

    @Test
    fun updateNote() {
        val note = noteService.updateNote(1, updateRequest)
        assertThat(note.id).isEqualTo(1)
        assertThat(note.color).isEqualTo(color1)
        assertThat(note.title).isEqualTo("update note")
    }

    @Test
    fun deleteNote() {
        noteService.deleteNote(3)
        Mockito.verify(noteRepository).findById(3)
        Mockito.verify(noteRepository).deleteById(3)
    }
}