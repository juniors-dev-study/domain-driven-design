package com.sns.user.component.user.domains

import org.hibernate.validator.constraints.URL
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
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
    val userId: String = "",    // TODO User.id 객체로

    @Max(50)
    val nickName: String = "",

    @Max(100)
    @URL
    val iconImageUrl: String = "",  // TODO URL 객체로?

    @Max(200)
    val intro: String = "", // 소개, 약력

    @Size(max = 5)
    val hobbyList: List<@NotBlank @Max(20) String> = emptyList(), // 취미

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN,
) : Persistable<String> {
    companion object {
        fun create(
            userId: String,
            nickName: String = "",
            iconImageUrl: String = "",
            intro: String = "",
            hobbyList: List<@NotBlank @Max(value = 20.toLong()) String> = emptyList(),
        ): Profile {
            return Profile(
                userId = userId,
                nickName = nickName,
                iconImageUrl = iconImageUrl,
                intro = intro,
                hobbyList = hobbyList,
            ).apply { new = true }
        }
    }

    @Transient
    private var new: Boolean = false

    override fun getId() = this.userId
    override fun isNew() = new
}

// TODO 고민들
/*
 1. Profile, User 관계를 어떻게 이을까
    - User 객체 안에 Profile?
      - user 가져올때마다, Profile 조회가 일어나서 안좋을듯?
    - Profile에 user PK를 넣어서 조회? :
      - 그러면 Profile만 있을때 User를 항상 조회해야함. 이런 경우가 많은가?
      - 그리고 User id 변경 시에, 변경필요 >> id를 한번 감싸면 좋을듯? UserId 만들어서
 2. notNull 할까?
    - table에 null 들어가기 vs empty string 들어가
*/
