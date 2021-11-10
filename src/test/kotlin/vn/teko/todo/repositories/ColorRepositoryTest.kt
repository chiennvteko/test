package vn.teko.todo.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class ColorRepositoryTest {

    @Autowired
    private lateinit var colorRepository: ColorRepository
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @BeforeEach
    fun setup() {
        val colorModel1 = ColorModel(
            id = 0,
            name = "aaa",
            code = "111",
        )
        val colorModel2 = ColorModel(
            id = 0,
            name = "bbb",
            code = "222",
        )
        val colorModel3 = ColorModel(
            id = 0,
            name = "ccc",
            code = "333",
        )
        entityManager.persist(colorModel1)
        entityManager.persist(colorModel2)
        entityManager.persist(colorModel3)
        entityManager.flush()
    }

    @Test
    fun findAll() {
        val colors = colorRepository.findAll().map { it.toColor() }
        assertThat(colors.size).isEqualTo(3)
        assertThat(colors.get(0).name).isEqualTo("aaa")
    }

    @Test
    fun findById() {
        val color = colorRepository.findById(2).map { it.toColor() }
        assertThat(color.get().id).isEqualTo(2)
        assertThat(color.get().name).isEqualTo("bbb")
    }

}