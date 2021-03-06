package vn.teko.todo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import vn.teko.todo.resquest.AddLabelRequest
import vn.teko.todo.resquest.UpdateLabelRequest
import vn.teko.todo.resquest.toLabel
import vn.teko.todo.services.Label
import vn.teko.todo.services.LabelService

@ExtendWith(SpringExtension::class)
@WebMvcTest(LabelController::class)
internal class LabelControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var labelservice: LabelService
    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val addRequest = AddLabelRequest(
        id = 0,
        name = "test add label 1"
    )
    private val updateRequest = UpdateLabelRequest(
        id = 0,
        name = "test update label 1"
    )
    @BeforeEach
    fun setup() {
        val label1 = Label(
            id = 1,
            name = "name1",
        )
        val label2 = Label(
            id = 2,
            name = "name2",
        )
        val label3 = Label(
            id = 3,
            name = "name3",
        )
        val label4 = Label(
            id = 4,
            name = "name4",
        )
        val labels = listOf<Label>(label1, label2, label3, label4)
        given(labelservice.getLabels()).willReturn(labels)
        given(labelservice.getLabel(1)).willReturn(label1)
        given(labelservice.getLabel(2)).willReturn(label2)
        given(labelservice.createLabel(addRequest.toLabel())).willReturn(
            Label(
            id = 5,
            name = "test add label 1",
        ))
        given(labelservice.updateLabel(1, updateRequest.toLabel())).willReturn(
            Label(
                id = 1,
                name = "test update label 1",
            ))
        given(labelservice.deleteLabel(3)).willReturn(label3)

    }


    @Test
    fun getLabels() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/labels").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("name2"))
            .andReturn()
    }

    @Test
    fun getLabelById() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/labels/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(labelservice.getLabel(1).name))
            .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.get("/api/labels/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(labelservice.getLabel(2).name))
            .andReturn()
    }


    @Test
    fun createLabel() {
        val requestJson = objectMapper.writeValueAsString(addRequest)
        mockMvc.perform(MockMvcRequestBuilders.post("/api/labels").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        Mockito.verify(labelservice).createLabel(addRequest.toLabel())
    }

    @Test
    fun updateLabel() {
        val requestJson = objectMapper.writeValueAsString(updateRequest)
        mockMvc.perform(MockMvcRequestBuilders.put("/api/labels/1").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        Mockito.verify(labelservice).updateLabel(1, updateRequest.toLabel())

    }

    @Test
    fun deleteLabel() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/labels/3").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        Mockito.verify(labelservice).deleteLabel(3)
    }


}