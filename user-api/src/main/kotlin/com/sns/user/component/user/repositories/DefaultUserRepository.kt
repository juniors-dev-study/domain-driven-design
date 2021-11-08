package com.sns.user.component.user.repositories

import org.springframework.stereotype.Repository

@Repository
class DefaultUserRepository(
    userCrudRepository: UserCrudRepository
) : UserRepository,
    UserCrudRepository by userCrudRepository
