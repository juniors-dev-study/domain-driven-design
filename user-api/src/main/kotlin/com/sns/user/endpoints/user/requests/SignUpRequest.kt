package com.sns.user.endpoints.user.requests

import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpRequest(
    @NotEmpty
    @Max(15)
    val name: String,

    @NotEmpty
    @Size(min = 8, max = 30, message = "비밀번호는 8자 이상 30자 미만이어야 합니다.")
    @Pattern(regexp = "(?=.*[A-z])(?=.*[0-9])", message = "비밀번호는 영문자와 숫자가 포함되어야 합니다.")
    val password: String,

    @NotEmpty
    @Email
    val email: String,
) {
}
