package vn.teko.todo.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ColorRepository: CrudRepository<ColorModel, Long> {

}