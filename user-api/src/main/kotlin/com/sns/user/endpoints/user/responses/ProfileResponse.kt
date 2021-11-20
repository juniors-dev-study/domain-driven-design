package com.sns.user.endpoints.user.responses

import com.sns.user.component.user.domains.Profile

/**
 * @author Hyounglin Jun
 */

data class ProfileResponse(
    val userId: String,
    val nickName: String?,
    val iconImageUrl: String,
    val intro: String?,
    val hobbies: List<String>?,
) {
    constructor(profile: Profile) : this(
        userId = profile.userId,
        nickName = profile.nickName,
        iconImageUrl = profile.getServiceIconImageUrl(),
        intro = profile.intro,
        hobbies = profile.hobbies?.map { e -> e.name }?.toList(),
    )
}
