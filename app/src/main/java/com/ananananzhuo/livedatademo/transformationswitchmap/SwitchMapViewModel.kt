package com.ananananzhuo.livedatademo.transformationswitchmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * author  :mayong
 * function:
 * date    :2021/6/19
 **/
class SwitchMapViewModel : ViewModel() {
    fun sendData() {
        userLivedata.value = SwitchUser("李白", 1200)
    }
    private val userLivedata = MutableLiveData<SwitchUser>()
    val mapLiveData = Transformations.switchMap(userLivedata) {
        changeUser(it!!)
    }
    private fun changeUser(it: SwitchUser): LiveData<String> {
        return MutableLiveData("${it.name}  的名字杜甫知道")
    }
}
data class SwitchUser(var name: String, var age: Int)