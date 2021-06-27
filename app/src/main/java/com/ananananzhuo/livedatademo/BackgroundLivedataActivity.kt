package com.ananananzhuo.livedatademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_background_livedata.*

/**
 * 实验在后台发送多个消息再返回，能收到的消息数量
 */
class BackgroundLivedataActivity : AppCompatActivity() {
    val viewModel by viewModels<BackgroundViewModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_livedata)
        btn_senddata_frombackground.setOnClickListener {
            viewModel.changeData()
        }
        btn_toanother_activity.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        viewModel.livedata.observe(this) {
            logEE("打印收到的数据：$it")
        }
    }
}

class BackgroundViewModel : ViewModel() {
    var count = 0
    val livedata = MutableLiveData<Int>()
    fun changeData() {
        val coundDown = object : CountDownTimer(1000*9, 1000 ) {
            override fun onTick(millisUntilFinished: Long) {
                count++
                livedata.value = count
//                livedata.postValue(count)
            }

            override fun onFinish() {
            }
        }

        coundDown.start()
    }


}