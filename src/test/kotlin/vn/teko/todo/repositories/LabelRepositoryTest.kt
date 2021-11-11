package vn.teko.todo.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import javax.persistence.PersistenceContext

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class LabelRepositoryTest {

    @Autowired
    private lateinit var labelRepository: LabelRepository
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun findAll() {
        val label1 = LabelModel(
            id = 0,
            name = "name1",
        )
        val label2 = LabelModel(
            id = 0,
            name = "name2",
        )
        val label3 = LabelModel(
            id = 0,
            name = "name3",
        )
        val label4 = LabelModel(
            id = 0,
            name = "name4",
        )
        entityManager.persist(label1)
        entityManager.persist(label2)
        entityManager.persist(label3)
        entityManager.persist(label4)
        val labels = labelRepository.findAll().map { it.toLabel() }
        assertThat(labels.size).isEqualTo(4)
        assertThat(labels.get(0).name).isEqualTo("name1")
    }

    @Test
    fun findById() {
        val label1 = LabelModel(
            id = 0,
            name = "sss",
        )
        entityManager.persist(label1)
        val label = labelRepository.findById(label1.id).get()
        assertThat(label).isEqualTo(label1)
    }

    @Test
    fun save() {
        val label1 = LabelModel(
            id = 0,
            name = "sss",
        )
        entityManager.persist(label1)
        val label = labelRepository.save(label1)
        entityManager.flush()
        assertThat(label).isEqualTo(label1)
    }

    fun deleteById() {
        labelRepository.deleteById(1)
        entityManager.flush();
        assertThat(labelRepository.findById(1).isPresent).isEqualTo(false)
    }

}