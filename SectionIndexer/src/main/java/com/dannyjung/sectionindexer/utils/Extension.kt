package com.dannyjung.sectionindexer.utils

import android.content.Context
import android.util.TypedValue

internal inline fun Context.spToPx(sp: Float): Int =
    TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            resources.displayMetrics
        )
        .toInt()
