package com.sns.user.endpoints.user.requests

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.Max

/**
 * @author Hyounglin Jun
 */
data class UpdateProfileRequest(
    @Max(50)
    val nickName: String?,

    @Max(100)
    @URL
    val iconImageUrl: String?,

    @Max(200)
    val intro: String?,

    val hobbies: MutableList<String>?
)
