package com.sns.authentication.user

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCrudRepository : CrudRepository<User, String> {
}
