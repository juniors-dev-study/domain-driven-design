package com.sns.user.components.test

import com.ninjasquad.springmockk.SpykBean
import com.sns.commons.service.EventPublisher
import com.sns.user.component.test.domains.TestUser
import com.sns.user.component.test.listeners.EmotionListener
import com.sns.user.component.test.repositories.TestUserRepository
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback

@Tag("init-test")
@Rollback
@SpringBootTest
class TestUserRepositoryTest @Autowired constructor(
    val repository: TestUserRepository,
    val eventPublisher: EventPublisher
) {

    @SpykBean
    lateinit var emotionListener: EmotionListener

    @ParameterizedTest
    @ValueSource(strings = ["hyounglin", "Chanhyeong Cho", "y2o2u2n", "youngvly"])
    fun insert(name: String) {
        Assumptions.assumeThat(name).isNotEmpty
        assertThat(repository.save(TestUser(name))).isNotNull
        assertThat(repository.findByNickName(name)).isNotNull
    }

    @Test
    @DisplayName("repository.save 호출시 이벤트 리스너 동작.")
    fun laughing_on_crud_repo() {
        val user = TestUser(nickName = "NEW_USER")
        user.happy() {
            eventPublisher.publish(it)
        }
        repository.save(user)

        verify(exactly = 1) { emotionListener.onLaughing(any()) }
    }
}
