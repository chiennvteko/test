package vn.teko.todo.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vn.teko.todo.repositories.NoteColorModel
import vn.teko.todo.repositories.NoteColorRepository

@RestController
@RequestMapping("/api/test")
class test(
    private val noteColorRepository: NoteColorRepository
) {
    @GetMapping
    fun getAll(): ResponseEntity< MutableIterable<NoteColorModel>> {
        val noteColorModel = noteColorRepository.findAll()
        return ResponseEntity.ok(noteColorModel)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(
        @PathVariable id: Long,
    ): ResponseEntity<NoteColorModel> {
        val note = noteColorRepository.findById(id).get()
        noteColorRepository.deleteById(id)
        return ResponseEntity.ok(note)
    }

    @DeleteMapping("/aa/{id}")
    fun deleteNote1(
        @PathVariable id: Long,
    ): ResponseEntity<NoteColorModel> {
        val note = noteColorRepository.findByNoteId(id)
        noteColorRepository.deleteByNoteId(id)
        return ResponseEntity.ok(note)
    }


}