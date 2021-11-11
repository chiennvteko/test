package vn.teko.todo.services

import org.springframework.stereotype.Service
import vn.teko.todo.exception.NotFoundException
import vn.teko.todo.repositories.*
import java.time.LocalDateTime
import java.util.*

@Service
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val noteColorRepository: NoteColorRepository,
    private val colorRepository: ColorRepository,
    private val noteLabelModelRepository: NoteLabelModelRepository,
    private val labelRepository: LabelRepository,
) : NoteService {

    override fun getNotes(): List<Note> {
        val notes = noteRepository.findAll().map { it.toNote(noteColorRepository.getnotecolor(it.id), colorRepository.findById(noteColorRepository.getnotecolor(it.id)).get().toColor()) }
        notes.map {
            it.labels = noteLabelModelRepository.getLabelByNote(it.id).map { it.toLabel() }
        }
        return notes
    }
    override fun getNote(id: Long): Note {
        val optionalNoteModel = noteRepository.findById(id).orElseThrow { NotFoundException(message = "not found noteId = $id ") }
        val colorId = noteColorRepository.getnotecolor(optionalNoteModel.id)
        val note = optionalNoteModel.toNote(colorId, colorRepository.findById(colorId).get().toColor())
        note.apply {
            labels = noteLabelModelRepository.getLabelByNote(note.id).map { it.toLabel() }
        }
        return note
    }

    fun addLabelToNote(noteId: Long, labelId: Long) {
        if (labelRepository.findById(labelId).isPresent() == false)  return
        noteLabelModelRepository.save(NoteLabelModel(
            noteId = noteId,
            labelId = labelId,
        ))
    }

    override fun createNote(addNote: Note): Note {
        val note = noteRepository.save(addNote.toNoteModel()).toNote(addNote.colorId, colorRepository.findById(addNote.colorId).get().toColor())
        note.apply {
            labelIds = addNote.labelIds
        }
        noteColorRepository.save(NoteColorModel(
            noteId = note.id,
            colorId = note.colorId
        ))
        note.labelIds.forEach {
            this.addLabelToNote(note.id, it)
        }
        note.apply {
            labels = noteLabelModelRepository.getLabelByNote(note.id).map { it.toLabel() }
        }
        return note
    }


    override fun updateNote(id: Long, newNote: Note): Note {
        val optionalNoteModel = noteRepository.findById(id).orElseThrow { NotFoundException(message = "not found noteId = $id ") }
        val colorId = noteColorRepository.getnotecolor(optionalNoteModel.id)
        var note = optionalNoteModel.toNote(colorId, colorRepository.findById(colorId).get().toColor())
        noteColorRepository.deleteByNoteId(note.id)
        noteColorRepository.save(NoteColorModel(
            noteId = note.id,
            colorId = newNote.colorId
        ))
        note.apply {
            this.title = newNote.title
            this.content = newNote.content
            this.colorId = newNote.colorId
            this.color = colorRepository.findById(newNote.colorId).get().toColor()
            this.labelIds = newNote.labelIds
            this.editedAt = LocalDateTime.now()
        }
        noteRepository.save(note.toNoteModel())
        noteLabelModelRepository.deleteNoteLabelModelByNoteId(note.id)
        note.labelIds.forEach {
            this.addLabelToNote(note.id, it)
        }
        note.apply {
            labels = noteLabelModelRepository.getLabelByNote(note.id).map { it.toLabel() }
        }
        return note
    }

    override fun deleteNote(id: Long): Note {
        val optionalNoteModel = noteRepository.findById(id).orElseThrow { NotFoundException(message = "not found noteId = $id ") }
        val colorId = noteColorRepository.getnotecolor(optionalNoteModel.id)
        val note  = optionalNoteModel.toNote(colorId, colorRepository.findById(colorId).get().toColor())
        note.apply {
            labels = noteLabelModelRepository.getLabelByNote(note.id).map { it.toLabel() }
        }
        noteRepository.deleteById(id)
        return note
    }
}
