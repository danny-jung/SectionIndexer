package com.dannyjung.sectionindexer

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

abstract class SectionIndexerItem(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  abstract fun bind(sectionIndex: SectionIndex)
}
