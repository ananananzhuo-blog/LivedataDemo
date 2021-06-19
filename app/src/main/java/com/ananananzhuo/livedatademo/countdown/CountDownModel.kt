package com.ananananzhuo.livedatademo.countdown

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * author  :mayong
 * function:
 * date    :2021/6/19
 **/
class CountDownModel : ViewModel() {

    val countDownLivedata = MutableLiveData<String>()
    private var remainSecond = 2000//剩余秒数

    init {
        val countDown = object : CountDownTimer(2000 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainSecond--
                countDownLivedata.postValue("剩余：${remainSecond} 秒")
            }

            override fun onFinish() {
                countDownLivedata.postValue("倒计时结束")
            }
        }
        countDown.start()
    }
}