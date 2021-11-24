package com.sns.user.core.config.db.converter

/**
 * @author Hyounglin Jun
 */
class EntityList<E>(
    val elements: List<E>? = emptyList(),
) {
    override fun toString(): String = elements?.joinToString(",") ?: ""
}
