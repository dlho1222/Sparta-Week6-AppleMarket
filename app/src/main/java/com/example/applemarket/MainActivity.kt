package com.example.applemarket

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarket.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView() //화면 세팅

        binding.ivNotification.setOnClickListener {
            notification()
        }
    }

    private fun initRecyclerView() { // 리사이클러뷰 화면 셋팅
        productAdapter = ProductAdapter(object : OnClickListener {
            override fun itemClick(product: Product) {
                toDetailActivity(product)
            }

        })
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
            val divider = DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL)//divider
            addItemDecoration(divider)
        }

    }

    private fun toDetailActivity(product: Product) {//디테일 페이지 이동
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(PRODUCT, product)
        }
        startActivity(intent)
    }

    override fun onBackPressed() { //종료 다이얼로그
        AlertDialog.Builder(this).apply {
            setIcon(R.drawable.speech_bubble)
            setTitle("종료")
            setMessage("정말 종료하시겠습니까?")
            setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            setPositiveButton("확인") { _, _ -> super.onBackPressed() }
            show()
        }
    }

    private fun notification() { // 채널 생성

        val channel =
            NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = this.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(
                R.drawable.apple,
            )
            .setShowWhen(true)
            .setContentTitle("키워드 알림")
            .setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
            .build()
        notificationManager.notify(100, notification)
    }
}

