package com.ananananzhuo.livedatademo.transformationswitchmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ananananzhuo.livedatademo.R
import com.ananananzhuo.livedatademo.logEE
import kotlinx.android.synthetic.main.activity_trans_switch_map.*

class TransSwitchMapActivity : AppCompatActivity() {
    private val model: SwitchMapViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_switch_map)
        model.mapLiveData.observe(this, {
            logEE(it)
            tv_switchmap.text=it
        })
        btn_switchmap.setOnClickListener {
            model.sendData()
        }
    }
}