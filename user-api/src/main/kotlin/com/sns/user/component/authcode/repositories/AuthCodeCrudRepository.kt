package com.sns.user.component.authcode.repositories

import com.sns.user.component.authcode.domain.AuthCode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthCodeCrudRepository : CrudRepository<AuthCode, Int> {
}
