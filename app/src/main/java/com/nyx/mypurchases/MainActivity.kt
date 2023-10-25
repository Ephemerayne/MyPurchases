package com.nyx.mypurchases

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.nyx.mypurchases.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setupActionBar(
        title: String?,
        chipText: String? = null,
//        onCategoryClick: (() -> Unit)? = null,
        onAddProductClick: (() -> Unit)? = null,
    ) {
        binding.appBarText.text = title
        binding.appBarCategoryChip.text = chipText

        if (chipText != null) {
            binding.appBarCategoryChip.visibility = View.VISIBLE
            binding.addProduct.visibility = View.VISIBLE
            binding.addProduct.setOnClickListener { onAddProductClick?.invoke() }
            //   binding.appBarCategoryChip.setOnClickListener { onCategoryClick?.invoke() }
        } else {
            binding.appBarCategoryChip.visibility = View.INVISIBLE
            binding.addProduct.visibility = View.GONE
        }
    }
}