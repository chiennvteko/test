package vn.teko.todo.controllers

import javassist.bytecode.annotation.LongMemberValue
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vn.teko.todo.services.LabelService
import vn.teko.todo.resquest.AddLabelRequest
import vn.teko.todo.resquest.UpdateLabelRequest
import vn.teko.todo.resquest.toLabel
import javax.validation.Valid

@RestController
@RequestMapping("/api/labels")
class LabelController(
    private val labelService: LabelService,
) {

    @GetMapping
    fun getLabels(): ResponseEntity<List<LabelDto>> {
        val labelDtos = labelService.getLabels().map { it.toLabelDto() }
        return ResponseEntity.ok(labelDtos)
    }

    @GetMapping("/{id}")
    fun getLabelById(
        @PathVariable id: Long,
    ): ResponseEntity<LabelDto> {
        return ResponseEntity.ok(labelService.getLabel(id).toLabelDto())
    }

    @PostMapping
    fun createLabel(
        @Valid @RequestBody request: AddLabelRequest,
    ): ResponseEntity<LabelDto> {
        return ResponseEntity.ok(labelService.createLabel(request.toLabel()).toLabelDto())
    }

    @PutMapping
    fun updateLabel(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateLabelRequest,
    ): ResponseEntity<LabelDto> {
        return  ResponseEntity.ok(labelService.updateLabel(id, request.toLabel()).toLabelDto())
    }

    @DeleteMapping("/{id}")
    fun  deleteLabel(
        @PathVariable id: Long,
     ): ResponseEntity<LabelDto> {
        return ResponseEntity.ok(labelService.deleteLabel(id).toLabelDto())
    }

}

