package com.example.applemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarket.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        val product = intent.getParcelableExtra<Product>("product")
        product?.let {
            binding.ivItemImage.setImageResource(it.imageFile)
            binding.tvTitle.text = it.title
            binding.tvSeller.text = it.seller
            binding.tvLocation.text = it.location
            binding.tvContents.text = it.contents
            binding.tvPrice.text = "${it.price}원"
        }
    }
}