package com.sns.user.core.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class AlreadyExistException(msg: String = "이미 존재합니다.") : RuntimeException(msg)
