package com.sns.authentication.user

import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable

data class User(
    @Id
    @NotBlank
    @Max(50)
    @JvmField
    val id: String, // email

    @NotBlank
    @Max(100)
    var password: String,

    @NotBlank
    @Max(50)
    val name: String
) : Persistable<String> {
    @Transient
    private var new: Boolean = false

    override fun getId() = this.id
    override fun isNew() = new
}
