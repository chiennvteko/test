package vn.teko.todo.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository: CrudRepository<LabelModel, Long> {

}
