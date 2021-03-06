package com.sns.user.component.test.listeners

import com.sns.commons.annotation.CustomEventListener
import com.sns.commons.utils.log
import com.sns.user.component.test.dtos.LaughingEvent

@CustomEventListener
class EmotionListener {
    private val log = log()

    fun onLaughing(event: LaughingEvent) {
        log.error("${event.who} is laughing")
    }
}
