package com.sns.user.endpoints.user.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length

data class SignUpRequest(
    @NotEmpty
    @Length(max = 15)
    val name: String,
    @NotEmpty
    @Length(min = 8, max = 30)
    val password: String,
    @NotEmpty
    @Email
    val email: String,
) {
}
