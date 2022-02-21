package com.dannyjung.sectionindexer.sample.utils

import java.util.regex.Pattern

private const val CHOSEONG_COUNT = 19
private const val JUNGSEONG_COUNT = 21
private const val JONGSEONG_COUNT = 28
private const val HANGUL_SYLLABLE_COUNT = CHOSEONG_COUNT * JUNGSEONG_COUNT * JONGSEONG_COUNT
private const val HANGUL_SYLLABLES_BASE = 0xAC00
private const val HANGUL_SYLLABLES_END = HANGUL_SYLLABLES_BASE + HANGUL_SYLLABLE_COUNT

private val CHOSEONG_COLLECTION = charArrayOf(
    'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
    'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
)

private fun Char.getChoseongIndex(): Int =
    (code - HANGUL_SYLLABLES_BASE) / (JUNGSEONG_COUNT * JONGSEONG_COUNT)

private fun Char.isSyllable(): Boolean =
    code in HANGUL_SYLLABLES_BASE until HANGUL_SYLLABLES_END

fun Char.getChoseong(): Char? {
    if (!isSyllable()) return null

    return CHOSEONG_COLLECTION.getOrNull(getChoseongIndex())
}

fun Char.isHangul(): Boolean = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", toString())

fun Char.isAlphabet(): Boolean = Pattern.matches("^[a-zA-Z]*$", toString())

fun Char.isNumber(): Boolean = Pattern.matches("^[0-9]*$", toString())
