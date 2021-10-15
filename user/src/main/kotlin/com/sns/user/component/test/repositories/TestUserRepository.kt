package com.sns.user.component.test.repositories

import com.sns.user.component.test.domains.TestUser
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TestUserRepository(val jdbcTemplate: JdbcTemplate) : ITestUserRepository {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun save(nickName: String): Int {
        return jdbcTemplate.update("""INSERT INTO test_user (`nick_name`) VALUES(?)""", nickName)
    }

    override fun save(user: TestUser): Int {
        return jdbcTemplate.update("""INSERT INTO test_user (`nick_name`) VALUES(?)""", user.nickName)
    }

    override fun findByNickName(nickName: String): TestUser {
        return jdbcTemplate.queryForObject(
            """
                SELECT id, nick_name
                FROM test_user
                WHERE nick_name = ?
                LIMIT 1
            """,
            TestUser.MAPPER, nickName
        ) ?: TestUser()
    }
}
