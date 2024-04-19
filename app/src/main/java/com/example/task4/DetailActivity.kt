package com.example.task4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.task4.databinding.ActivityDetailBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val dataList = MySingleton.getInstance().getDataList()

    //    private val getData: Data? by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            intent.getParcelableExtra("DATA", Data::class.java)
//        }else{
//            intent.getParcelableExtra("DATA")
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val getData = intent.getParcelableExtra<Data>("DATA")

        binding.detailTitle.text = getData!!.title
        binding.detailContent.text = getData.content
        binding.detailAddress.text = getData.address
        binding.detailSeller.text = getData.seller
        val dec = DecimalFormat("#,###원")
        binding.detailPrice.text = dec.format(getData.price)
        binding.detailImage.setImageResource(getData.img)


        binding.backButton.setOnClickListener {
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            val getPosition = intent.getIntExtra("position",0)
            intent.putExtra("position",getPosition)
            finish()
        }
        binding.detailGoodImage.setOnClickListener {
            detailGoodCheck()
        }


    }

    private fun detailGoodCheck(){
        val getPosition = intent.getIntExtra("position",0)
        if (dataList[getPosition].goodCheck) {
            binding.detailGoodImage.setImageResource(R.drawable.good_false)
            dataList[getPosition].good -= 1
            dataList[getPosition].goodCheck = false
            Log.d("확인","${dataList[getPosition].good}, ${dataList[getPosition].goodCheck} ")

        } else {
            binding.detailGoodImage.setImageResource(R.drawable.good_true)
            dataList[getPosition].good += 1
            dataList[getPosition].goodCheck = true
            Log.d("확인","${dataList[getPosition].good}, ${dataList[getPosition].goodCheck} ")
        }
    }
}