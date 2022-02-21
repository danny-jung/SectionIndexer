package com.dannyjung.sectionindexer.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dannyjung.sectionindexer.SectionIndex
import com.dannyjung.sectionindexer.SectionIndexer
import com.dannyjung.sectionindexer.sample.databinding.ActivityMainBinding
import com.dannyjung.sectionindexer.sample.utils.getChoseong
import com.dannyjung.sectionindexer.sample.utils.isAlphabet
import com.dannyjung.sectionindexer.sample.utils.isHangul
import com.dannyjung.sectionindexer.sample.utils.isNumber

class MainActivity : AppCompatActivity() {

    private val viewBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { Adapter() }

    private val testData by lazy { getTestData() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initUi()
    }

    private fun initUi() {
        viewBinding.recyclerView.adapter = adapter.apply { submitList(testData) }

        viewBinding.sectionIndexer.onTouchListener = object : SectionIndexer.OnTouchListener {
            override fun onTouchDown(sectionIndex: SectionIndex) {
                sectionIndex.scrollToPosition()
            }

            override fun onTouchMove(sectionIndex: SectionIndex) {
                sectionIndex.scrollToPosition()
            }

            override fun onTouchUp(sectionIndex: SectionIndex) {
                sectionIndex.scrollToPosition()
            }

            override fun onTouchCancel() = Unit
        }

        viewBinding.sectionIndexer.setSectionIndices(createSectionIndices(testData))
    }

    private fun createSectionIndices(brands: List<Brand>): List<SectionIndex> {
        val brandIndices = brands
            .asSequence()
            .mapIndexedNotNull { index, brand ->
                brand.name.firstOrNull()?.let {
                    when {
                        it.isHangul() -> {
                            it.getChoseong()?.let { choseong ->
                                choseong to index
                            }
                        }
                        it.isAlphabet() -> {
                            it.uppercaseChar() to index
                        }
                        it.isNumber() -> {
                            it to index
                        }
                        else -> {
                            null
                        }
                    }
                }
            }
            .distinctBy { it.first }
            .toMap()

        val hangulIndices = brandIndices.filter { it.key.isHangul() }.toList()
        val alphabetIndices = brandIndices.filter { it.key.isAlphabet() }.toList()
        val numberIndices = brandIndices.filter { it.key.isNumber() }.toList()

        return HANGUL_SECTION_INDICES
            .map<SectionIndex> {
                SectionIndex.Text(
                    name = it.toString(),
                    position = brandIndices[it] ?: RecyclerView.NO_POSITION
                )
            }
            .run {
                mapIndexed { index, sectionIndex ->
                    if (sectionIndex is SectionIndex.HasPosition &&
                        sectionIndex.position == RecyclerView.NO_POSITION
                    ) {
                        when (sectionIndex) {
                            is SectionIndex.Text -> {
                                sectionIndex.copy(position = getPreviousPosition(index))
                            }
                            is SectionIndex.Icon -> {
                                sectionIndex.copy(position = getPreviousPosition(index))
                            }
                            is SectionIndex.Intermediate -> {
                                sectionIndex
                            }
                        }
                    } else {
                        sectionIndex
                    }
                }
            }
            .toMutableList()
            .apply {
                add(
                    SectionIndex.Text(
                        name = "A",
                        position = alphabetIndices.firstOrNull()?.second
                            ?: hangulIndices.lastOrNull()?.second
                            ?: RecyclerView.NO_POSITION
                    )
                )
                add(
                    SectionIndex.Intermediate(name = "•")
                )
                add(
                    SectionIndex.Text(
                        name = "Z",
                        position = alphabetIndices.lastOrNull()?.second
                            ?: hangulIndices.lastOrNull()?.second
                            ?: RecyclerView.NO_POSITION
                    )
                )

                add(
                    SectionIndex.Text(
                        name = "0",
                        position = numberIndices.firstOrNull()?.second
                            ?: alphabetIndices.lastOrNull()?.second
                            ?: hangulIndices.lastOrNull()?.second
                            ?: RecyclerView.NO_POSITION
                    )
                )
                add(
                    SectionIndex.Intermediate(name = "•")
                )
                add(
                    SectionIndex.Text(
                        name = "9",
                        position = numberIndices.lastOrNull()?.second
                            ?: alphabetIndices.lastOrNull()?.second
                            ?: hangulIndices.lastOrNull()?.second
                            ?: RecyclerView.NO_POSITION
                    )
                )
            }
    }

    private fun List<SectionIndex>.getPreviousPosition(currentIndex: Int): Int =
        getOrNull(currentIndex - 1)
            ?.let {
                if (it is SectionIndex.HasPosition) {
                    if (it.position == RecyclerView.NO_POSITION) {
                        getPreviousPosition(currentIndex - 1)
                    } else {
                        it.position
                    }
                } else {
                    getPreviousPosition(currentIndex - 1)
                }
            }
            ?: 0

    private fun SectionIndex.scrollToPosition() {
        val position = when (this) {
            is SectionIndex.HasPosition -> {
                position
            }
            else -> {
                null
            }
        }

        if (position != null && position != RecyclerView.NO_POSITION) {
            (viewBinding.recyclerView.layoutManager as? LinearLayoutManager)
                ?.scrollToPositionWithOffset(position, 0)
        }
    }

    companion object {

        private val HANGUL_SECTION_INDICES = charArrayOf(
            'ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        )
    }
}
