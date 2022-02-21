package com.dannyjung.sectionindexer

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import com.dannyjung.sectionindexer.utils.dp

class SimpleSectionIndexerItem(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : SectionIndexerItem(context, attrs, defStyleAttr) {

  init {
    gravity = Gravity.CENTER
    textSize = context.dp(12).toFloat()
    setTextColor(Color.BLACK)
  }

  override fun bind(sectionIndex: SectionIndex) {
    when (sectionIndex) {
      is SectionIndex.Text -> {
        text = sectionIndex.name
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
      }
      is SectionIndex.Icon -> {
        text = null
        setCompoundDrawablesWithIntrinsicBounds(sectionIndex.resId, 0, 0, 0)
      }
      is SectionIndex.Intermediate -> {
        text = sectionIndex.name
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
      }
    }
  }
}
