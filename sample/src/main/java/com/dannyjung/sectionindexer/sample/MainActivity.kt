package com.dannyjung.sectionindexer.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dannyjung.sectionindexer.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}
