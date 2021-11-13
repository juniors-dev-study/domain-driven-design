package com.sns.user.core.exceptions

class NoAuthorityException(msg: String? = "권한이 없습니다") : RuntimeException(msg) {
}
