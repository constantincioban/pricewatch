package com.konovus.pricewatchai.data.utils

fun List<Any>.toNavigationPath(): String {
    return if (this.isEmpty()) {
        ""
    } else {
        this.joinToString(separator = "/", prefix = "/") { it.toString() }
    }
}