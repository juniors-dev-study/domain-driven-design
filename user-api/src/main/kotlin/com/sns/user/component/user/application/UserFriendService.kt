package com.sns.user.component.user.application

import com.sns.commons.service.EventPublisher
import com.sns.user.component.user.domains.FriendRequest
import com.sns.user.component.user.repositories.FriendRequestRepository
import com.sns.user.component.user.repositories.UserRepository
import com.sns.user.core.exceptions.AlreadyExistException
import com.sns.user.core.exceptions.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserFriendService(
    private val eventPublisher: EventPublisher,
    private val userRepository: UserRepository,
    private val friendRequestRepository: FriendRequestRepository,
) {

    @Transactional
    fun breakFriendship(userId: String, friendUserId: String) {
        val user = userRepository.findById(userId).orElseThrow {
            NotFoundException("요청을 수행한 사용자의 정보가 없습니다")
        }

        val friendUser = userRepository.findById(userId).orElseThrow {
            NotFoundException("친구 관계를 끊을 대상 사용자 정보가 없습니다")
        }

        user.breakFriendship(friendUser) {
            eventPublisher.publish(it)
        }

        friendUser.breakFriendship(user, deletedByFriend = true) {
            eventPublisher.publish(it)
        }

        userRepository.saveAll(listOf(user, friendUser))
    }

    @Transactional
    fun createFriendRequest(userId: String, friendUserId: String): FriendRequest {
        val requester = userRepository.findById(userId).orElseThrow {
            NotFoundException("친구 요청을 보낸 사용자 정보가 없습니다")
        }

        val receiver = userRepository.findById(friendUserId).orElseThrow {
            NotFoundException("친구 요청을 받을 사용자 정보가 없습니다")
        }

        if (friendRequestRepository.findByRequesterIdAndReceiverId(userId, friendUserId).isPresent) {
            throw AlreadyExistException("이미 진행 중인 요청이 있습니다")
        }

        val friendRequest = requester.requestFriend(receiver) {
            eventPublisher.publish(it)
        }

        return friendRequestRepository.save(friendRequest)
    }

    @Transactional
    fun deleteFriendRequest(userId: String, friendRequestId: Long) {
        userRepository.findById(userId).orElseThrow {
            NotFoundException("친구 요청을 보낸 사용자 정보가 없습니다")
        }

        val friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow {
            NotFoundException("해당 친구 요청이 존재하지 않습니다")
        }

        friendRequest.delete(actorUserId = userId) {
            eventPublisher.publish(it)
        }

        friendRequestRepository.deleteById(friendRequestId)
    }

    @Transactional
    fun approveFriendRequest(userId: String, friendRequestId: Long) {
        val friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow {
            NotFoundException("해당 친구 요청이 존재하지 않습니다")
        }

        val createFriendAction: FriendRequest.() -> Unit = {
            val requester = userRepository.findById(requesterId).orElseThrow {
                NotFoundException("친구 요청을 보낸 사용자 정보가 없습니다")
            }
            val receiver = userRepository.findById(receiverId).orElseThrow {
                NotFoundException("친구 요청을 받을 사용자 정보가 없습니다")
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
    fun rejectFriendRequest(userId: String, friendRequestId: Long) {
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
