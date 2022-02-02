package com.sns.article.endpoints.reaction

import com.sns.article.component.reaction.domains.ReactionTargetType
import com.sns.article.component.reaction.domains.ReactionType
import com.sns.article.endpoints.reaction.requests.ReactionCreateRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import security.WithLoginUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class ReactionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithLoginUser
    fun create() {
        val request = ReactionCreateRequest(
            targetType = ReactionTargetType.ARTICLE,
            targetId = 1L,
            ReactionType.LIKE,
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/reactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encodeToString(request)),
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun find() {
    }

    @Test
    fun delete() {
    }
}
