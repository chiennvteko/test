package vn.teko.todo.exception

import org.springframework.http.HttpStatus
import java.sql.Timestamp
import java.time.LocalDateTime

data class ErrorDetails(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val httpStatus: HttpStatus,
    val message: String?,
)