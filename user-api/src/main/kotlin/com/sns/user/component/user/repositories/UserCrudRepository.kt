package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCrudRepository : CrudRepository<User, String>
