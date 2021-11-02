package com.sns.commons

interface DomainEvent {
    val eventId: String

    val channel: String
}
