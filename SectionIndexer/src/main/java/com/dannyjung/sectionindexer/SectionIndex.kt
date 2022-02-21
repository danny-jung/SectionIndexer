package com.dannyjung.sectionindexer

import androidx.annotation.DrawableRes

sealed class SectionIndex {

  interface HasPosition {
    val position: Int
  }

  data class Text(
    val name: String,
    override val position: Int
  ) : SectionIndex(), HasPosition

  data class Icon(
    @DrawableRes val resId: Int,
    override val position: Int
  ) : SectionIndex(), HasPosition

  data class Intermediate(
    val name: String
  ) : SectionIndex()
}
