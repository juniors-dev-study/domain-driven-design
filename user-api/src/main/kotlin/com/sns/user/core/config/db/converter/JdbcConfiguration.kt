package com.sns.user.core.config.db.converter

import com.sns.commons.config.JdbcConfiguration
import com.sns.user.component.user.domains.UserId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.ConditionalGenericConverter
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.core.convert.BasicJdbcConverter
import org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.core.convert.RelationResolver
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.lang.Nullable
import org.springframework.util.ClassUtils

/**
 * @author Hyounglin Jun
 */
@Configuration
class JdbcConfiguration : JdbcConfiguration() {
    override fun jdbcCustomConversions(): JdbcCustomConversions {
        println("hello im jdbc2")
        return JdbcCustomConversions(
            listOf(
                UserIdToStringConverter(),
                StringToUserIdConverter(),
            ),
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
