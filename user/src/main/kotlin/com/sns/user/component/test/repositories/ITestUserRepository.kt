package com.sns.user.component.test.repositories

import com.sns.user.component.test.domains.TestUser

interface ITestUserRepository {
    fun save(nickName: String): Int
    fun save(user: TestUser): Int
    fun findByNickName(nickName: String): TestUser
}
