package com.silverthread.tftstatistics.util

import android.content.Context
import androidx.annotation.StringRes

fun Context.getWinRate(@StringRes id: Int, win: Int, games: Int): String {
    val winRate = if (games > 0) 100f * win / games else 0f
    return String.format(resources.getString(id), winRate)
}