package vn.teko.todo.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException) : ResponseEntity<ErrorDetails> {
        val error = ErrorDetails(
            httpStatus = HttpStatus.NOT_FOUND,
            message = exception.message
        )
        return ResponseEntity(error, error.httpStatus)
    }
}