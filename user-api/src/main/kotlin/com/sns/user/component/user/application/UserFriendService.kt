package com.sns.user.component.user.application

import com.sns.commons.service.EventPublisher
import com.sns.user.component.user.domains.FriendRequest
import com.sns.user.component.user.repositories.FriendRequestRepository
import com.sns.user.component.user.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserFriendService(
    private val eventPublisher: EventPublisher,
    private val userRepository: UserRepository,
    private val friendRequestRepository: FriendRequestRepository,
) {

    @Transactional
    fun createFriendRequest(userId: String, friendUserId: String): FriendRequest {
        val requester = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("친구 요청을 보낸 사용자 정보가 없습니다")
        }

        val receiver = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("친구 요청을 받을 사용자 정보가 없습니다")
        }

        if (friendRequestRepository.findByRequesterIdAndReceiverId(userId, friendUserId).isPresent) {
            throw IllegalStateException("이미 진행 중인 요청이 있습니다")
        }

        val friendRequest = requester.requestFriend(receiver) {
            eventPublisher.publish(it)
        }

        return friendRequestRepository.save(friendRequest)
    }

    @Transactional
    fun approveFriendRequest(userId: String, friendRequestId: Int) {
        val friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow {
            NoSuchElementException("해당 친구 요청이 존재하지 않습니다")
        }

        val createFriendAction: FriendRequest.() -> Unit = {
            val requester = userRepository.findById(requesterId).orElseThrow {
                NoSuchElementException("친구 요청을 보낸 사용자 정보가 없습니다")
            }
            val receiver = userRepository.findById(receiverId).orElseThrow {
                NoSuchElementException("친구 요청을 받을 사용자 정보가 없습니다")
            }

            // 사용자
            requester.friendRequestApproved(receiver)
            receiver.friendRequestReceived(requester)
        }

        friendRequest.approve(actorUserId = userId, createFriendAction) {
            eventPublisher.publish(it)
        }

        // 완료된 요청은 제거
        friendRequestRepository.delete(friendRequest)
    }

    @Transactional
    fun rejectFriendRequest(userId: String, friendRequestId: Int) {
        val friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow {
            NoSuchElementException("해당 친구 요청이 존재하지 않습니다")
        }

        friendRequest.reject(actorUserId = userId) {
            eventPublisher.publish(it)
        }

        // 거절된 요청은 제거
        friendRequestRepository.delete(friendRequest)
    }
}
