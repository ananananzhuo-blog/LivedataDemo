package com.ananananzhuo.livedatademo.meditorlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ananananzhuo.livedatademo.R
import com.ananananzhuo.livedatademo.logEE
import kotlinx.android.synthetic.main.activity_meditor_livedata.*

class MeditorLivedataActivity : AppCompatActivity() {
    private val model by viewModels<MeditorLiveViewModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditor_livedata)
        model.liveCombind.observe(this){
            logEE(it)
            tv_count.text=it
        }
        btn_livedata1.setOnClickListener {
            model.setData1("李白")
        }
        btn_livedata2.setOnClickListener {
            model.setData2(1000)
        }
    }
}