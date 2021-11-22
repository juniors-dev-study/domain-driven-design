package com.sns.commons

import java.time.Instant

abstract class DomainEvent(
    val channel: String,
    private val uniqueId: Any,
    val createdAt: Instant = Instant.now(),
    val eventId: String = "$channel-$uniqueId-${System.currentTimeMillis()}"
)
