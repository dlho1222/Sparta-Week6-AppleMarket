package com.example.applemarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter(object : OnClickListener {
            override fun itemClick(product: Product) {
                toDetailActivity(product)
            }

        })
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
            val divider = DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

    }

    private fun toDetailActivity(product: Product) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("product", product)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setIcon(R.drawable.speech_bubble)
            setTitle("종료")
            setMessage("정말 종료하시겠습니까?")
            setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            setPositiveButton("확인") { _, _ -> super.onBackPressed() }
            show()
        }
    }

}