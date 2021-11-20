package com.sns.commons.utils

inline fun Boolean?.ifTrue(block: Boolean.() -> Unit): Boolean? {
    if (this == true) {
        block()
    }
    return this
}

inline fun Boolean?.ifFalse(block: Boolean.() -> Unit): Boolean? {
    if (this == true) {
        block()
    }
    return this
}
