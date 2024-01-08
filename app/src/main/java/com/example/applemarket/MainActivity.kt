package com.example.applemarket

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private val updatedLike =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val updatedLike = result.data?.getBooleanExtra(UPDATED_LIKE, false) ?: false
            val position = result.data?.getIntExtra(PRODUCT_POSITION, 1) ?: 1
            if (result.resultCode == RESULT_OK) {
                updatedUi(updatedLike, position)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView() //화면 세팅

        binding.ivNotification.setOnClickListener {
            notification()
        }
    }
    private fun updatedUi(updatedLike: Boolean, position: Int) {//like상태 변화
        val product = ProductList.list[position - 1]  //list의 인덱스
        product.isLiked = updatedLike
        productAdapter.notifyItemChanged(position - 1)
    }


    private fun initRecyclerView() { // 리사이클러뷰 화면 셋팅
        productAdapter = ProductAdapter(object : OnClickListener {
            //itemView 클릭시 toDetailActivity()
            override fun itemClick(product: Product) {
                toDetailActivity(product)
            }
            //itemView 롱 클릭시 showRemoveDialog()
            override fun longClick(product: Product) {
                showRemoveDialog(product)
            }
        })
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
            val divider = DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL)//divider 추가
            addItemDecoration(divider)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                //스크롤 감지
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // 스크롤을 아래로 내릴 때 // 스크롤을 위로 올릴 때
                    if (dy > 0) binding.fbScrollButton.show() else if (dy < 0) binding.fbScrollButton.hide()
                }
            })
            binding.fbScrollButton.setOnClickListener {//스크롤 이동
                smoothScrollToPosition(0)
            }

        }
    }

    private fun toDetailActivity(product: Product) {//디테일 페이지 이동
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(PRODUCT, product)
        }
        updatedLike.launch(intent)
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

    private fun notification() {
        //notification 채널 생성
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

    private fun showRemoveDialog(product: Product) { //롱 클릭시 확인을 누르면 아이템 삭제 다이얼로그
        AlertDialog.Builder(this).apply {
            setIcon(R.drawable.speech_bubble)
            setTitle("종료")
            setMessage("상품을 정말로 삭제하시겠습니까?")
            setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            setPositiveButton("확인") { _, _ ->
                ProductList.remove(product)
                productAdapter.notifyDataSetChanged()
            }
            show()
        }
    }

}

