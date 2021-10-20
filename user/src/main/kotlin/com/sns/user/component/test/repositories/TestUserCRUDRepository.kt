package com.sns.user.component.test.repositories

import com.sns.user.component.test.domains.TestUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TestUserCRUDRepository : CrudRepository<TestUser, Int>
