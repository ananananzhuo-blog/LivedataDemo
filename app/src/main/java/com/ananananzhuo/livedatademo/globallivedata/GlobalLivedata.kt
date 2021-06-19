package com.ananananzhuo.livedatademo.globallivedata

import android.os.CountDownTimer
import androidx.lifecycle.LiveData

/**
 * author  :mayong
 * function:
 * date    :2021/6/19
 **/
class GlobalLivedata : LiveData<String>() {
    val coundManager = CountDownManager()
    val listener = object : OnDataChangeListener {
        override fun change(data: String) {
           postValue(data)
        }
    }
    override fun onActive() {
        super.onActive()
        coundManager.setListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        coundManager.removeListener(listener)
    }
    companion object {
        private lateinit var globalData: GlobalLivedata
        fun getInstance(): GlobalLivedata {
            globalData = if (::globalData.isInitialized) globalData else GlobalLivedata()
            return globalData
        }
    }
}