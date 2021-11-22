package com.sns.user.core.config.db.converter

import com.sns.user.component.user.domains.UserId
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration

/**
 * @author Hyounglin Jun
 */
@Configuration
class JdbcConfiguration : AbstractJdbcConfiguration() {
    override fun jdbcCustomConversions(): JdbcCustomConversions {
        return JdbcCustomConversions(
            listOf(UserIdToStringConverter(), StringToUserIdConverter()),
        )
    }
}

@WritingConverter
class UserIdToStringConverter : Converter<UserId, String> {
    override fun convert(source: UserId): String? {
        return source.getId()
    }
}

@ReadingConverter
class StringToUserIdConverter : Converter<String, UserId> {
    override fun convert(source: String): UserId {
        return UserId(source)
    }
}
