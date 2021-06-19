package com.ananananzhuo.livedatademo.globallivedata

import android.os.CountDownTimer

/**
 * author  :mayong
 * function:
 * date    :2021/6/19
 **/
class CountDownManager {
    private var remainSecond = 2000//剩余秒数
    private val listeners = mutableListOf<OnDataChangeListener>()

    init {
        val countDown = object : CountDownTimer(2000 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainSecond--
                callback("剩余：${remainSecond} 秒")
            }

            override fun onFinish() {
                callback("倒计时结束")
            }
        }
        countDown.start()
    }

    /**
     * 循环遍历回调消息
     */
    private fun callback(msg:String) {
        for (listener in listeners){
            listener.change(msg)
        }
    }

    fun setListener(listener: OnDataChangeListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: OnDataChangeListener) {
        listeners.remove(listener)
    }
}

interface OnDataChangeListener {
    fun change(data: String)
}