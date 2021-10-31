package com.sns.user.component.test.repositories

import com.sns.commons.utils.log
import com.sns.user.component.test.domains.TestUser
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DefaultTestUserRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val testUserCRUDRepository: TestUserCRUDRepository
) : TestUserRepository,
    CrudRepository<TestUser, Int> by testUserCRUDRepository {
    private val log = log()

    override fun save(nickName: String): Int {
        return jdbcTemplate.update("INSERT INTO test_user (`nick_name`) VALUES(?)", nickName)
    }

    override fun save(user: TestUser): Int {
        return jdbcTemplate.update("INSERT INTO test_user (`nick_name`) VALUES(?)", user.nickName)
    }

    override fun findByNickName(nickName: String): TestUser {
        return jdbcTemplate.queryForObject(
            """
                SELECT id, nick_name
                FROM test_user
                WHERE nick_name = ?
                LIMIT 1
            """,
            TestUser.MAPPER, nickName,
        ) ?: TestUser()
    }
}
