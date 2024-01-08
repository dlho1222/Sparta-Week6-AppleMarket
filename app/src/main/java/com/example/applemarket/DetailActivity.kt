package com.example.applemarket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applemarket.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val decimalFormat = DecimalFormat("#,###")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        val product = intent.getParcelableExtra<Product>(PRODUCT)

        //백버튼 처리
        binding.ivBackButton.setOnClickListener {
            //val product = intent.getParcelableExtra<Product>(PRODUCT)
            Intent().apply {
                putExtra(UPDATED_LIKE, product?.isLiked)
                putExtra(PRODUCT_POSITION, product?.position)
                setResult(RESULT_OK, this)
            }
            finish()
        }

        //좋아요 버튼 처리
        product?.let {
            binding.ivLike.setOnClickListener {
                product.isLiked = !product.isLiked
                if (product.isLiked) binding.ivLike.setImageResource(R.drawable.heart) else binding.ivLike.setImageResource(
                    R.drawable.empty_heart
                )
                if (product.isLiked) Snackbar.make(
                    binding.root,
                    "관심 목록에 추가되었습니다.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        //product의 상태에 따라 heart이미지 변경
        val product = intent.getParcelableExtra<Product>(PRODUCT)
        product?.let {
            if (product.isLiked) binding.ivLike.setImageResource(R.drawable.heart) else binding.ivLike.setImageResource(
                R.drawable.empty_heart
            )
        }
        super.onResume()
    }

    private fun initViews() {
        //초기 ui 설정
        val product = intent.getParcelableExtra<Product>(PRODUCT)
        product?.let {
            binding.ivItemImage.setImageResource(it.imageFile)
            binding.tvTitle.text = it.title
            binding.tvSeller.text = it.seller
            binding.tvLocation.text = it.location
            binding.tvContents.text = it.contents
            "${decimalFormat.format(it.price)}원".also { binding.tvPrice.text = it }
        }
    }
}
