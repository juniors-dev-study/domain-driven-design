package com.sns.user.component.user.domains

import com.sns.user.core.config.db.converter.EntityList
import org.hibernate.validator.constraints.URL
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * 사용자 프로필 도메인 객체(VO)
 * User 객체와 1:1 관계
 * @author Hyounglin Jun
 */
data class Profile(
    @Id
    @NotNull
    @Max(50)
    val userId: String,

    @Max(50)
    val nickName: String?,

    @Max(100)
    @URL
    val iconImageUrl: String?,  // TODO URL 객체로?

    @Max(200)
    val intro: String?, // 소개, 약력

    @Size(max = 5)
    val hobbies: EntityList<String>?, // 취미 목록

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN,
) : Persistable<String> {
    companion object {
        fun create(
            userId: String,
            nickName: String? = null,
            iconImageUrl: String? = null,
            intro: String? = null,
            hobbies: List<String>? = null,
        ): Profile {
            return Profile(
                userId = userId,
                nickName = nickName,
                iconImageUrl = iconImageUrl,
                intro = intro,
                hobbies = EntityList(hobbies),
            ).apply { new = true }
        }
    }

    @Transient
    private var new: Boolean = false

    override fun isNew() = new
    override fun getId() = this.userId

    fun getServiceIconImageUrl(): String {
        return if (iconImageUrl.isNullOrBlank()) PROFILE_DEFAULT_ICON_IMAGE_URL else iconImageUrl
    }
}

data class Hobby(
    val name: String,
)

const val PROFILE_DEFAULT_ICON_IMAGE_URL: String = "https://ssl.pstatic.net/static/kin/09renewal/avatar/200x200_m_gray/public.png"
