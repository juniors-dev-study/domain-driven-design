package com.sns.user

import org.assertj.core.api.Assertions.assertThat
import java.util.*
import java.util.function.Consumer

infix fun <T : Any> T?.isEqualTo(other: T?) = assertThat(this).isEqualTo(other)

infix fun <T : Any> T?.isNotEqualTo(other: T?) = assertThat(this).isNotEqualTo(other)

infix fun <T : Any> T.satisfies(requirements: Consumer<T>) = assertThat(this).satisfies(requirements)

infix fun <T : Any, R : Optional<T>> R.hasValueSatisfying(requirements: Consumer<T>) = assertThat(this).hasValueSatisfying(requirements)
