package com.ananananzhuo.livedatademo.meditorlivedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

/**
 * author  :mayong
 * function:
 * date    :2021/6/20
 **/
class MeditorLiveViewModel : ViewModel() {

    var count =0//计数字段
    fun setData1(name: String) {
        liveData1.value = name
    }
    fun setData2(age: Int) {
        liveData2.value = age
    }
    private val liveData1 = MutableLiveData<String>()
    private val liveData2 = MutableLiveData<Int>()
    val liveCombind = MediatorLiveData<String>()
    init {
        liveCombind.addSource(liveData1) {
            increase()
        }
        liveCombind.addSource(liveData2) {
           increase()
        }
    }
    private fun increase() {
        count++
        if(count>=10){
            liveCombind.value="安安安安卓同学，您已经点击${count}次，再点我也不跟你玩了，收手吧。。。"
        }
    }
}