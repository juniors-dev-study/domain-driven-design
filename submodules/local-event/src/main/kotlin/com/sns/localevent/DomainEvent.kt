package com.sns.localevent

interface DomainEvent {
    val eventId: String

    val channel: String
}
