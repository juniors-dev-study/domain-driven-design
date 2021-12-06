package com.sns.user.endpoints.user

import com.sns.user.component.user.application.UserQueryService
import security.WithLoginUser
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(controllers = [UserController::class])       //EnableJdbcAuditing 관련 autoConfigure 제외 필요
class UserControllerTest @Autowired constructor(
) {
    @MockBean
    lateinit var userQueryService: UserQueryService

    @Autowired
    lateinit var mockMvc: MockMvc

    @DisplayName("로그인 유저가 인증되면, 결과가 리턴되어야 한다.")
    @WithLoginUser
    @Test
    internal fun checkLogin_1() {
        mockMvc.perform(get("/api/v1/users/checkLogin"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("user@gmail.com")))
    }

    @DisplayName("비로그인 유저이면 UnAuthorized 이어야한다.")
    @WithAnonymousUser
    @Test
    internal fun checkLogin_2() {
        mockMvc.perform(get("/api/v1/users/checkLogin"))
            .andExpect(status().isUnauthorized)
    }
}
