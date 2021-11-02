package vn.teko.todo.controllers

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import vn.teko.todo.resquest.AddNoteRequest
import vn.teko.todo.resquest.UpdateNoteRequest
import vn.teko.todo.resquest.toNote
import vn.teko.todo.services.ColorService
import vn.teko.todo.services.NoteService
import javax.validation.Valid

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteSevice: NoteService,
    private val colorService: ColorService
) {

    @GetMapping
    fun getNotes(): ResponseEntity<List<NoteDto>> {
        val noteDtos = noteSevice.getNotes().map { it.toNoteDto() }
        return ResponseEntity.ok(noteDtos)
    }

    @GetMapping("/{id}")
    fun getNote(
        @PathVariable id: Long,
    ): ResponseEntity<NoteDto> {
        val note = noteSevice.getNote(id)
        return ResponseEntity.ok(note.toNoteDto())
    }

    @PostMapping
    fun createNote(
        @Valid @RequestBody request: AddNoteRequest,
    ): ResponseEntity<NoteDto> {
        val note = noteSevice.createNote(request.toNote())
        return ResponseEntity.ok(note.toNoteDto())
    }

    @PutMapping("/{id}")
    fun updateNote(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateNoteRequest,
    ): ResponseEntity<NoteDto> {
        val note = noteSevice.updateNote(id, request.toNote())
        return ResponseEntity.ok(note.toNoteDto())
    }

    @DeleteMapping("/{id}")
    fun deleteNote(
        @PathVariable id: Long,
    ): ResponseEntity<NoteDto> {
        val note = noteSevice.deleteNote(id)
        return ResponseEntity.ok(note.toNoteDto())
    }

}