package vn.teko.todo.services

import org.springframework.stereotype.Service
import vn.teko.todo.exception.NotFoundException
import vn.teko.todo.repositories.*
import java.time.LocalDateTime

@Service
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val noteColorRepository: NoteColorRepository,
    private val colorRepository: ColorRepository,
    private val noteLabelModelRepository: NoteLabelModelRepository
) : NoteService {

    override fun getNotes(): List<Note> {
        val notes = noteRepository.findAll().map { it.toNote(noteRepository.getnotecolor(it.id), colorRepository.findById(noteRepository.getnotecolor(it.id)).get().toColor()) }
        notes.map {
            it.labels = noteLabelModelRepository.getLabelByNote(it.id).map { it.toLabel() }
        }
        return notes
    }
    override fun getNote(id: Long): Note {
        val optionalNoteModel = noteRepository.findById(id).orElseThrow { NotFoundException(message = "not found noteId = $id ") }
        val colorId = noteRepository.getnotecolor(optionalNoteModel.id)
        val note = optionalNoteModel.toNote(colorId, colorRepository.findById(colorId).get().toColor())
        note.apply {
            labels = noteLabelModelRepository.getLabelByNote(note.id).map { it.toLabel() }
        }
        return note
    }

    override fun createNote(note: Note): Note {
        val note = noteRepository.save(note.toNoteModel()).toNote(note.colorId, colorRepository.findById(note.colorId).get().toColor() )
        noteColorRepository.save(NoteColorModel(
            noteId = note.id,
            colorId = note.colorId
        ))
        return note
    }

    override fun updateNote(id: Long, newNote: Note): Note {
        val optionalNoteModel = noteRepository.findById(id).orElseThrow { NotFoundException(message = "not found noteId = $id ") }
        val colorId = noteRepository.getnotecolor(optionalNoteModel.id)
        val note = optionalNoteModel.toNote(colorId, colorRepository.findById(colorId).get().toColor())
        noteColorRepository.deleteByNoteId(note.id)
        noteColorRepository.save(NoteColorModel(
            noteId = note.id,
            colorId = newNote.colorId
        ))
        note.apply {
            this.title = newNote.title
            this.content = newNote.content
            this.colorId = newNote.colorId
            this.editedAt = LocalDateTime.now()
        }

        return noteRepository.save(note.toNoteModel()).toNote(note.colorId, colorRepository.findById(note.colorId).get().toColor())
    }

    override fun deleteNote(id: Long): Note {
        val optionalNoteModel = noteRepository.findById(id).orElseThrow { NotFoundException(message = "not found noteId = $id ") }
        val colorId = noteRepository.getnotecolor(optionalNoteModel.id)
        val note  = optionalNoteModel.toNote(colorId, colorRepository.findById(colorId).get().toColor())
        noteRepository.deleteById(id)
        return note
    }
}
