package com.sns.commons.utils

fun <T> T?.notNull(): T = requireNotNull(this)
