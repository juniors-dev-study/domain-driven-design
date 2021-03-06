package com.sns.user.component.test.domains

import com.sns.commons.DomainEvent
import com.sns.user.component.test.dtos.LaughingEvent
import org.springframework.data.annotation.Id
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper

data class TestUser(
    val nickName: String = "anonymous"
) {
    @Id
    private var id: Int? = null

    fun happy(publish: (DomainEvent) -> Unit = { _ -> }) {
        publish(LaughingEvent(nickName))
    }

    companion object {
        val MAPPER: RowMapper<TestUser> = BeanPropertyRowMapper.newInstance(TestUser::class.java)
    }
}
