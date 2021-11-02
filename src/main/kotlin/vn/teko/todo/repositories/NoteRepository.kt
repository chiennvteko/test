package vn.teko.todo.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository: CrudRepository<NoteModel, Long> {
    @Query("SELECT n.colorId FROM NoteColorModel n WHERE n.noteId = :noteId")
    fun getnotecolor(@Param("noteId") noteId: Long) : Long
}