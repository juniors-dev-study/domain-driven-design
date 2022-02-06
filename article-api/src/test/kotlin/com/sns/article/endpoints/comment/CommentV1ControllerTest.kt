package com.sns.article.endpoints.comment

import com.sns.article.component.comment.domains.RootType
import com.sns.article.endpoints.comment.requests.CommentCreateRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import security.WithLoginUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class CommentV1ControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithLoginUser
    fun create() {
        val rootId = 999
        val request = CommentCreateRequest(RootType.ARTICLE, rootId = rootId.toString(), "contents")
        mockMvc.perform(
            post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encodeToString(request)),
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}
