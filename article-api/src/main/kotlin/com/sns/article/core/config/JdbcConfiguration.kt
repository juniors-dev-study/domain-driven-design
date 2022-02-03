package com.sns.article.core.config

import com.sns.article.component.article.domains.ArticleId
import com.sns.commons.config.JdbcConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.ConditionalGenericConverter
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.core.convert.*
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.lang.Nullable
import org.springframework.util.ClassUtils
import java.math.BigInteger

/**
 * @author Hyounglin Jun
 */
@Configuration
class JdbcConfiguration : JdbcConfiguration() {

    override fun jdbcCustomConversions(): JdbcCustomConversions {
        return JdbcCustomConversions(
            listOf(
                ArticleIdToIntConverter(),
                IntToArticleIdConverter(),
                // ArticleIdToBigIntegerConverter(),
                // BigIntegerToArticleIdConverter(),
            ),
        )
    }
}

@WritingConverter
class ArticleIdToIntConverter : Converter<ArticleId, Int> {
    override fun convert(source: ArticleId): Int {
        return source.id
    }
}

@ReadingConverter
class IntToArticleIdConverter : Converter<Int, ArticleId> {
    override fun convert(source: Int): ArticleId {
        return ArticleId(source)
    }
}

/*
01:37:23 [http-nio-10002-exec-1][ERROR] Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [java.math.BigInteger] to type [com.sns.article.component.article.domains.ArticleId]] with root cause (DirectJDKLog.java:175)
org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [java.math.BigInteger] to type [com.sns.article.component.article.domains.ArticleId]
TODO 이거 때문에 임시 사용
 */
@WritingConverter
class ArticleIdToBigIntegerConverter : Converter<ArticleId, BigInteger> {
    override fun convert(source: ArticleId): BigInteger {
        return BigInteger(source.id.toString())
    }
}

@ReadingConverter
class BigIntegerToArticleIdConverter : Converter<BigInteger, ArticleId> {
    override fun convert(source: BigInteger): ArticleId {
        return ArticleId(source.toInt())
    }
}
