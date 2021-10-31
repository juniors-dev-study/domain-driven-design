package com.sns.user.component.test.repositories

import com.sns.user.component.test.domains.TestUser

interface TestUserRepository {
    fun save(nickName: String): Int
    fun save(user: TestUser): TestUser
    fun findByNickName(nickName: String): TestUser
}
