package com.example.task4

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "alarm"   // Channel for notification
    private var notificationManager: NotificationManager? = null
    private val dataList = MySingleton.getInstance().getDataList()
    private val adapter = Adapter(dataList)

    //뒤로가기 버튼
    val backPressCallBack = object : OnBackPressedCallback(true) {
        //뒤로가기 버튼 눌렀을 때 이벤트 처리
        override fun handleOnBackPressed() {
            val dialog = AlertDialog.Builder(this@MainActivity)
            dialog.setTitle("앱을 종료하시겠습니까?")

            val dialogListener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    finish()
                }
            }

            dialog.setPositiveButton("종료", dialogListener)
            dialog.setNegativeButton("취소", null)
            dialog.show()
        }
    }

    //바인딩
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this, backPressCallBack) // 뒤로가기 버튼 연결

        enableEdgeToEdge()

        //툴바
        setSupportActionBar(binding.toolbar)


        //알림 실행
        createNotificationChannel(CHANNEL_ID, "test")
        var alarm = binding.alarm
        alarm.setOnClickListener {
            notificationRun()
        }


        //리스트 클릭시 실행
        adapter.itemClick = object : Adapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                val data = Data(
                    dataList[position].num,
                    dataList[position].img,
                    dataList[position].title,
                    dataList[position].content,
                    dataList[position].seller,
                    dataList[position].price,
                    dataList[position].address,
                    dataList[position].good,
                    dataList[position].chat,
                    dataList[position].goodCheck
                )
                intent.putExtra("position",position)
                intent.putExtra("DATA", data)
                startActivity(intent)
            }
        }

    }

    //알림 채널 생성
    private fun createNotificationChannel(channelId: String, name: String) {
        //26 버전 이상이면 채널 등록
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
            }
            // 시스템에 채널 등록
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    //알림 설정
    private fun notificationRun() {
        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setWhen(System.currentTimeMillis())
            .setContentTitle("키워드 알림.")
            .setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
            .build()
        notificationManager?.notify(10, notification)
    }

    override fun onResume() {
        super.onResume()
        createRecyclerView()
        var position = intent.getIntExtra("position",0)
        adapter.notifyItemChanged(position,dataList.size)
    }

    private fun createRecyclerView(){
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)
    }

}