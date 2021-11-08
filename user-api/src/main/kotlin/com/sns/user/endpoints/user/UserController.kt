package com.sns.user.endpoints.user

import com.sns.user.component.user.application.UserQueryService
import com.sns.user.component.user.domains.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userQueryService: UserQueryService
) {
    @GetMapping("/ids/{id}")
    fun getUser(@PathVariable id: String): User? = userQueryService.getById(id)
}
