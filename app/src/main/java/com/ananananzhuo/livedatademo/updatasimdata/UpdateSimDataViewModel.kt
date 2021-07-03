package com.ananananzhuo.livedatademo.updatasimdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * author  :mayong
 * function:
 * date    :2021/7/3
 **/
class UpdateSimDataViewModel: ViewModel() {
    val livedata = MutableLiveData<Int>()
    fun setData(){
        livedata.value=124
    }
}