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
            listOf(UserIdToStringConverter(), StringToUserIdConverter(), EnListToStringConverter(), StringToEnListConverter()),
        )
    }
}

@WritingConverter
class EnListToStringConverter : Converter<EntityList<String>, String> {
    override fun convert(source: EntityList<String>): String {
        return source.toString()
    }
}

@ReadingConverter
class StringToEnListConverter : Converter<String, EntityList<String>> {
    override fun convert(source: String): EntityList<String> {
        return EntityList(source.split(".").toList())
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
