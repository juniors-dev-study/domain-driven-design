package com.sns.article.core.config

import com.sns.article.component.article.domains.ArticleId
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

/**
 * @author Hyounglin Jun
 */
@Configuration
class JdbcConfiguration : AbstractJdbcConfiguration() {
    @Bean
    override fun jdbcConverter(
        mappingContext: JdbcMappingContext,
        operations: NamedParameterJdbcOperations,
        relationResolver: RelationResolver,
        conversions: JdbcCustomConversions,
        dialect: Dialect,
    ): JdbcConverter {
        val jdbcTypeFactory = DefaultJdbcTypeFactory(operations.jdbcOperations)

        val baseConverter = BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory, dialect.identifierProcessing)
        val jdbcConverter = object : JdbcConverter by baseConverter {
            /**
             * 기존 collection 을 array 로 변환 (List<String> -> String[])
             * 변경: collection 을 내부 클래스로 변환 (List<String> -> String)
             */
            override fun getColumnType(property: RelationalPersistentProperty): Class<*> {
                return if (property.isCollectionLike && !property.isEntity) {
                    ClassUtils.resolvePrimitiveIfNecessary(property.actualType)
                } else {
                    baseConverter.getColumnType(property)
                }
            }
        }

        // 기본으로 들어가는 Collection -> String, Collection -> Object, String -> Object converter 제거
        val conversionService = jdbcConverter.conversionService as DefaultConversionService
        conversionService.removeConvertible(String::class.java, Collection::class.java)
        conversionService.removeConvertible(Collection::class.java, String::class.java)
        conversionService.removeConvertible(Collection::class.java, Object::class.java)

        // 별도 추가한 converter 추가
        conversionService.addConverter(CollectionToStringConverter())
        conversionService.addConverter(StringToCollectionConverter())

        return jdbcConverter
    }

    override fun jdbcCustomConversions(): JdbcCustomConversions {
        return JdbcCustomConversions(
            listOf(
                ArticleIdToIntConverter(),
                IntTOArticleIdConverter(),
            ),
        )
    }
}

@WritingConverter
class ArticleIdToIntConverter : Converter<ArticleId, Int> {
    override fun convert(source: ArticleId): Int? {
        return source.id
    }
}

@ReadingConverter
class IntTOArticleIdConverter : Converter<Int, ArticleId> {
    override fun convert(source: Int): ArticleId {
        return ArticleId(source)
    }
}

@ReadingConverter
class StringToCollectionConverter : ConditionalGenericConverter {
    override fun getConvertibleTypes(): Set<ConvertiblePair> {
        return setOf(ConvertiblePair(String::class.java, MutableCollection::class.java))
    }

    override fun matches(sourceType: TypeDescriptor, targetType: TypeDescriptor): Boolean {
        return sourceType.type == String::class.java && MutableCollection::class.java.isAssignableFrom(targetType.type)
    }

    @Nullable
    override fun convert(@Nullable source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? {
        if (source == null) {
            return null
        }
        val string = source as String

        return string.split(":").toList()
    }
}

@WritingConverter
class CollectionToStringConverter : ConditionalGenericConverter {
    override fun getConvertibleTypes(): Set<ConvertiblePair> {
        return setOf(ConvertiblePair(MutableCollection::class.java, String::class.java))
    }

    override fun matches(sourceType: TypeDescriptor, targetType: TypeDescriptor): Boolean {
        return MutableCollection::class.java.isAssignableFrom(sourceType.type) && targetType.type == String::class.java
    }

    @Nullable
    override fun convert(@Nullable source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? {
        if (source == null) {
            return null
        }
        val collection = source as Collection<*>

        return collection.joinToString(":")
    }
}
