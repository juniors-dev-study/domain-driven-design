package com.sns.user.endpoints.user

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import com.sns.commons.utils.log
import com.sns.user.component.user.application.UserQueryService
import com.sns.user.component.user.domains.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userQueryService: UserQueryService
) {
    val log = this.log()

    @GetMapping("/ids/{id}")
    fun getUser(@PathVariable id: String): User? = userQueryService.getById(id)

    @IsLoginUser
    @GetMapping("/checkLogin")
    fun checkLogin(loginUser: LoginUser): String {
        val auth = SecurityContextHolder.getContext().authentication
        log.error("principal : {} {} {} {}", auth.authorities, auth.principal, auth.name, auth.details)
        return "Hi ${loginUser.name}, this is user-api"
    }
}
