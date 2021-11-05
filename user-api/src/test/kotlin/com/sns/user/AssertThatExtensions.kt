package com.sns.user

import java.util.*
import java.util.function.Consumer
import org.assertj.core.api.Assertions.assertThat

infix fun <T : Any> T.isEqualTo(other: T) = assertThat(this).isEqualTo(other)

infix fun <T : Any> T.satisfies(requirements: Consumer<T>) = assertThat(this).satisfies(requirements)

infix fun <T : Any, R : Optional<T>> R.hasValueSatisfying(requirements: Consumer<T>) = assertThat(this).hasValueSatisfying(requirements)
