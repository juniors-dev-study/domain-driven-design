package com.sns.user.core.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author Hyounglin Jun
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NotExistException(msg: String = "해당 데이터가 없습니다.") : RuntimeException(msg)
