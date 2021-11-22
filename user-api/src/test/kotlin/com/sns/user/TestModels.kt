package com.sns.user

import com.sns.user.component.user.domains.User

fun createUser(id: String): User = User.create(id, "1235", "TESTER")
