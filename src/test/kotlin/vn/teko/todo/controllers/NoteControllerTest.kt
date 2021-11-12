package vn.teko.todo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import vn.teko.todo.resquest.AddNoteRequest
import vn.teko.todo.resquest.UpdateNoteRequest
import vn.teko.todo.resquest.toNote
import vn.teko.todo.services.Note
import vn.teko.todo.services.NoteService
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(NoteController::class)
internal class NoteControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var noteService: NoteService
    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val addRequest = AddNoteRequest(
        id = 0,
        title = "add note",
        content = "ssss",
        colorId =  1,
    )
    private val updateRequest = UpdateNoteRequest(
        id = 1,
        title = "update note",
        content = "ssss",
        colorId =  1,
    )


    @BeforeEach
    fun setUp() {
        val note1 = Note(
            id = 1,
            title = "111",
            content = "note 1",
            colorId = 1,
            createAt = LocalDateTime.now(),
            editedAt = LocalDateTime.now(),
        )
        val note2 = Note(
            id = 2,
            title = "222",
            content = "note 2",
            colorId = 1,
            createAt = LocalDateTime.now(),
            editedAt = LocalDateTime.now(),
        )
        val note3 = Note(
            id = 3,
            title = "333",
            content = "note 3",
            colorId = 1,
            createAt = LocalDateTime.now(),
            editedAt = LocalDateTime.now(),
        )
        val notes = listOf<Note>(note1, note2, note3)
        given(noteService.getNotes()).willReturn(notes)
        given(noteService.getNote(1)).willReturn(note1)
        given(noteService.getNote(2)).willReturn(note2)
        given(noteService.createNote(addRequest.toNote())).willReturn(
            Note(
                id = 4,
                title = "add note",
                content = "ssss",
                colorId =  1,
                createAt = LocalDateTime.now(),
                editedAt = LocalDateTime.now(),
            )
        )
        given(noteService.updateNote(1, updateRequest.toNote())).willReturn(
            Note(
                id = 1,
                title = "update note",
                content = "ssss",
                colorId =  1,
                createAt = note1.createAt,
                editedAt = LocalDateTime.now(),
            )
        )
        given(noteService.deleteNote(3)).willReturn(note3)
    }

    @Test
    fun getNotes() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1].title").value("222"))
            .andReturn()
    }


    @Test
    fun getnoteById() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(noteService.getNote(1).title))
            .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(noteService.getNote(2).title))
            .andReturn()
    }



    @Test
    fun createnote() {
        val requestJson = objectMapper.writeValueAsString(addRequest)
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        Mockito.verify(noteService).createNote(addRequest.toNote())

    }

    @Test
    fun updatenote() {
        val requestJson = objectMapper.writeValueAsString(updateRequest)
        mockMvc.perform(MockMvcRequestBuilders.put("/api/notes/1").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        Mockito.verify(noteService).updateNote(1, updateRequest.toNote())

    }

    @Test
    fun deletenote() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/3").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        Mockito.verify(noteService).deleteNote(3)
    }
}