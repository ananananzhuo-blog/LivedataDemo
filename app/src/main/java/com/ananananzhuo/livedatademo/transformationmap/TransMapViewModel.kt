package com.ananananzhuo.livedatademo.transformationmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.function.Function

/**
 * author  :mayong
 * function:
 * date    :2021/6/19
 **/
class TransMapViewModel: ViewModel() {
    fun sendData() {
        userLivedata.value=User("李白",1200)
    }
    val userLivedata =MutableLiveData<User>()
    val mapLiveData = Transformations.map(userLivedata){
        "${it.name} : ${it.age}"
    }
}
data class User(var name:String,var age:Int)