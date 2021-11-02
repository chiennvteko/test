package vn.teko.todo.services

interface NoteService {
    fun getNotes() : List<Note>
    fun getNote(id: Long) : Note
    fun createNote(note: Note): Note
    fun updateNote(id: Long, newNote: Note) : Note
    fun deleteNote(id: Long) : Note
}
