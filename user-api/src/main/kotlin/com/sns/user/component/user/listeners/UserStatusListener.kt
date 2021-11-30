package com.sns.user.component.user.listeners

import com.sns.commons.annotation.CustomEventListener
import com.sns.user.component.authcode.application.AuthCodeCommandService
import com.sns.user.component.user.events.UserStatusChangedEvent
import com.sns.user.component.user.repositories.UserRepository
import org.springframework.transaction.annotation.Transactional

@CustomEventListener
class UserStatusListener(
    val authCodeCommandService: AuthCodeCommandService,
    val userRepository: UserRepository
) {
    // 인증 전, 기초 가입만 마친 상태
    fun onCreated(createdEvent: UserStatusChangedEvent) {
        val user = createdEvent.user
        authCodeCommandService.create(user)
    }

    fun onActivated(activatedEvent: UserStatusChangedEvent) {
        // TODO 타임라인생성, 프로필생성,,
    }

    @Transactional
    fun onDelete(deletedEvent: UserStatusChangedEvent) {
        // 알림? sync 맞춰야할 필요가 있을것같아서(예상) 실제 row 삭제를 listener에서 합니다.
        val user = deletedEvent.user
        userRepository.delete(user)
    }
}
