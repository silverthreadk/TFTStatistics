package com.silverthread.tftstatistics.util

fun String.getName(): String {
    val list = this.split('_')
    return if (list.size > 1) list[1] else this
}