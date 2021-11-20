package com.sns.user.core.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class NoAuthorityException(msg: String? = "권한이 없습니다") : RuntimeException(msg) {
}
