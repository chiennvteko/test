/*
package vn.teko.todo.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vn.teko.todo.repositories.NoteColorModel
import vn.teko.todo.repositories.NoteColorRepository
import vn.teko.todo.repositories.NoteLabelModel
import vn.teko.todo.repositories.NoteLabelModelRepository

@RestController
@RequestMapping("/api/test")
class test(
    private val noteLabelModelRepository: NoteLabelModelRepository
) {
    @GetMapping
    fun getAll(): ResponseEntity< MutableIterable<NoteLabelModel>> {
        val noteLabelModel = noteLabelModelRepository.findAll()
        return ResponseEntity.ok(noteLabelModel)
    }

    @DeleteMapping("note/{id}")
    fun deleteNote(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        noteLabelModelRepository.deleteNoteLabelModelByNoteId(id)
        return ResponseEntity.ok("delete label in note$id")
    }

    @DeleteMapping("/label/{id}")
    fun deleteNote1(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        noteLabelModelRepository.deleteNoteLabelModelByLabelId(id)
        return ResponseEntity.ok("delete label $id")
    }
    @DeleteMapping("/notelabel")
    fun delete(
        @RequestBody noteLabelModel: NoteLabelModel) : ResponseEntity<String> {
        noteLabelModelRepository.deleteNoteLabelModelByNoteIdAndLabelId(noteLabelModel.noteId, noteLabelModel.labelId)
        return ResponseEntity.ok("delete label ${noteLabelModel.labelId} in note ${noteLabelModel.noteId}")
    }


}

 */