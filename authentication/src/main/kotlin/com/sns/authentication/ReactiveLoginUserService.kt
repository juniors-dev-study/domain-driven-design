package com.sns.authentication

// import com.sns.authentication.user.UserCrudRepository
// import org.springframework.security.core.userdetails.ReactiveUserDetailsService
// import org.springframework.security.core.userdetails.UsernameNotFoundException
// import org.springframework.stereotype.Service
// import reactor.core.publisher.Mono
//
// @Service
// class ReactiveLoginUserService(
//     private val userCrudRepository: UserCrudRepository,
// ) : ReactiveUserDetailsService {
//
//     override fun findByUsername(username: String?): Mono<LoginUser> {
//         if (username.isNullOrEmpty()) throw UsernameNotFoundException("유저 정보를 찾을 수 없습니다")
//         return Mono.just(LoginUser.create(userCrudRepository.findById(username).orElse(null)))
//     }
// }
