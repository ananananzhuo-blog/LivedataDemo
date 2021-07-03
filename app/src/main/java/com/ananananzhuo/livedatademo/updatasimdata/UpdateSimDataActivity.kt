package com.ananananzhuo.livedatademo.updatasimdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ananananzhuo.livedatademo.R
import com.ananananzhuo.livedatademo.logEE
import kotlinx.android.synthetic.main.activity_update_sim_data.*

class UpdateSimDataActivity : AppCompatActivity() {
    val model by viewModels<UpdateSimDataViewModel> {
        ViewModelProvider.NewInstanceFactory()
    }
    var count =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_sim_data)
        btn_updatewith_simdata.setOnClickListener {
            model.setData()
        }
        model.livedata.observe(this){
            logEE("收到的数据是：${it}")
            count++
            tv_beupdate.text="收到livedata数据更新请求 $count 次"
        }
    }
}