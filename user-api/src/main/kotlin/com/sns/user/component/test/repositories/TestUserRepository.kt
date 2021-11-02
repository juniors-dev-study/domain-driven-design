package com.sns.user.component.test.repositories

import com.sns.user.component.test.domains.TestUser
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface TestUserRepository : CrudRepository<TestUser, Int> {
    fun save(nickName: String): Int
    fun findByNickName(nickName: String): TestUser
}
