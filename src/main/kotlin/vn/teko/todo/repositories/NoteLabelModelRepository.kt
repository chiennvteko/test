package vn.teko.todo.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface NoteLabelModelRepository: CrudRepository<NoteLabelModel, Long> {
    @Query("SELECT l FROM LabelModel l WHERE l.id IN (SELECT nl.labelId FROM NoteLabelModel nl WHERE nl.noteId = :noteId)")
    fun getLabelByNote(@Param("noteId")id: Long) : List<LabelModel>
    @Transactional
    fun deleteNoteLabelModelByNoteIdAndLabelId(noteId: Long, labelId: Long)
    @Transactional
    fun deleteNoteLabelModelByNoteId(noteId: Long)
    @Transactional
    fun deleteNoteLabelModelByLabelId(labelId: Long)
}