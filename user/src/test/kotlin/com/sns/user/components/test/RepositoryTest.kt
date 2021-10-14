package com.sns.user.components.test

import com.ninjasquad.springmockk.SpykBean
import com.sns.user.component.test.domains.TestUser
import com.sns.user.component.test.listeners.EmotionListener
import com.sns.user.component.test.repositories.ITestUserRepository
import com.sns.user.component.test.repositories.TestUserCRUDRepository
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension

@Tag("init-test")
@ExtendWith(SpringExtension::class)
@Rollback
@SpringBootTest
class RepositoryTest @Autowired constructor(
    val repository: ITestUserRepository,
    val crudRepository: TestUserCRUDRepository,
) {

    @SpykBean
    lateinit var emotionListener: EmotionListener

    @ParameterizedTest
    @ValueSource(strings = ["hyounglin", "Chanhyeong Cho", "y2o2u2n", "youngvly"])
    fun insert(name: String) {
        Assumptions.assumeThat(name).isNotEmpty
        assertThat(repository.save(name)).isPositive
        assertThat(repository.findByNickName(name)).isNotNull
    }

    @Test
    @DisplayName("CRUDRepository.save 호출시 이벤트 리스너 동작.")
    fun laughing_on_crud_repo() {
        val user = TestUser(nickName = "NEW_USER")
        user.happy()
        crudRepository.save(user)

        verify(exactly = 2) { emotionListener.actionLaughing(any()) }
        verify(exactly = 1) { emotionListener.onLaughing(any()) }
    }

    @Test
    @DisplayName("일반 @Repository.save 호출시 이벤트 리스너 미동작.")
    fun laughing_on_normal_repo() {
        val user = TestUser(nickName = "NEW_USER")
        user.happy()
        repository.save(user)

        verify(exactly = 0) { emotionListener.actionLaughing(any()) }
        verify(exactly = 0) { emotionListener.onLaughing(any()) }
    }
}
