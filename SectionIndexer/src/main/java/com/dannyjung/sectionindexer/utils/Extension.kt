package com.dannyjung.sectionindexer.utils

import android.content.Context

internal inline fun Context.dp(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()
