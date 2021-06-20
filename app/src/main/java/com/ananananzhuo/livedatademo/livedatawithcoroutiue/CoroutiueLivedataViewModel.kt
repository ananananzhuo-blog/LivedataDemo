package com.ananananzhuo.livedatademo.livedatawithcoroutiue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

/**
 * author  :mayong
 * function:
 * date    :2021/6/20
 **/
class CoroutiueLivedataViewModel : ViewModel() {
    /**
     * 开启异步任务
     */
    fun startAsyncWithSecond(second: Int): LiveData<String> = liveData<String> {
        delay(second * 1000L)
        emit("倒计时结束")
    }

    fun startAsyncEmitSource(second: Int)= liveData<String> {
        delay(second * 1000L)
        emit("${second} 秒阻塞完成,再阻塞三秒后通知你")
        val emitSourceLivedata = MutableLiveData<String>()
        emitSource(
            emitSourceLivedata
        )
        delay(second*1000L)
        emitSourceLivedata.value="再次阻塞${second}秒完成"
    }
}