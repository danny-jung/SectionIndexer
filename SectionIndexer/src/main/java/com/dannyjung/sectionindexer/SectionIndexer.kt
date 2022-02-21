package com.dannyjung.sectionindexer

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.core.view.children

class SectionIndexer @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  var onTouchListener: OnTouchListener? = null

  var sectionIndexerItem: () -> SectionIndexerItem = { SimpleSectionIndexerItem(context) }
    set(value) {
      if (value == field) return
      field = value

      clearSectionIndexYs()
      resetSectionIndexerItem()
    }

  private val sectionIndices = mutableListOf<SectionIndex>()
  private val yRangeSectionIndices = mutableMapOf<ClosedFloatingPointRange<Float>, SectionIndex>()

  init {
    orientation = VERTICAL
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    if (event.x < 0 || event.x > width) {
      onTouchListener?.onTouchCancel()
      return super.onTouchEvent(event)
    }

    val touchedSectionIndex = getTouchedSectionIndex(event.y)

    return when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        if (touchedSectionIndex != null) {
          onTouchListener?.onTouchDown(touchedSectionIndex)
        } else {
          onTouchListener?.onTouchCancel()
        }
        true
      }
      MotionEvent.ACTION_MOVE -> {
        if (touchedSectionIndex != null) {
          onTouchListener?.onTouchMove(touchedSectionIndex)
        } else {
          onTouchListener?.onTouchCancel()
        }
        true
      }
      MotionEvent.ACTION_UP -> {
        if (touchedSectionIndex != null) {
          onTouchListener?.onTouchUp(touchedSectionIndex)
        } else {
          onTouchListener?.onTouchCancel()
        }
        true
      }
      else -> super.onTouchEvent(event)
    }
  }

  fun setSectionIndices(sectionIndices: List<SectionIndex>) {
    this.sectionIndices.apply {
      clear()
      addAll(sectionIndices)
    }

    clearSectionIndexYs()
    resetSectionIndexerItem()
  }

  private fun clearSectionIndexYs() {
    yRangeSectionIndices.clear()
  }

  private fun resetSectionIndexerItem() {
    removeAllViews()
    this.sectionIndices.forEach { sectionIndex ->
      addView(
        sectionIndexerItem().apply {
          layoutParams = when (sectionIndex) {
            is SectionIndex.Text, is SectionIndex.Icon -> {
              LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)
            }
            is SectionIndex.Intermediate -> {
              LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            }
          }
          bind(sectionIndex)
        }
      )
    }
  }

  private fun getTouchedSectionIndex(y: Float): SectionIndex? {
    if (yRangeSectionIndices.isEmpty()) {
      yRangeSectionIndices.putAll(
        children
          .mapIndexedNotNull { index, view ->
            sectionIndices.getOrNull(index)?.let {
              view.y..(getChildAt(index + 1)?.y ?: height.toFloat()) to it
            }
          }
          .toMap()
      )
    }

    return yRangeSectionIndices.keys
      .find { it.contains(y) }
      ?.let { yRangeSectionIndices.getOrElse(it, { null }) }
  }

  interface OnTouchListener {

    fun onTouchDown(sectionIndex: SectionIndex)
    fun onTouchMove(sectionIndex: SectionIndex)
    fun onTouchUp(sectionIndex: SectionIndex)
    fun onTouchCancel()
  }
}
