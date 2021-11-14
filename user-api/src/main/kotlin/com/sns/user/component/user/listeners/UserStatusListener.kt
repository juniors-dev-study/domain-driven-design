package com.sns.user.component.user.listeners

import com.sns.commons.annotation.CustomEventListener
import com.sns.user.component.authcode.application.AuthCodeCommand
import com.sns.user.component.user.events.UserActivatedEvent
import com.sns.user.component.user.events.UserCreatedEvent

@CustomEventListener
class UserStatusListener(val authCodeCommand: AuthCodeCommand) {
    // 인증 전, 기초 가입만 마친 상태
    fun onCreated(createdEvent: UserCreatedEvent) {
        val user = createdEvent.user
        authCodeCommand.create(user.id)
    }

    fun onActivated(activatedEvent: UserActivatedEvent) {
        // TODO 타임라인생성, 프로필생성,,
    }
}
