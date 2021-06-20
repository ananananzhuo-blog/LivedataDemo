package com.ananananzhuo.livedatademo.livedatawithcoroutiue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ananananzhuo.livedatademo.R
import com.ananananzhuo.livedatademo.logEE
import kotlinx.android.synthetic.main.activity_coroutiue_livedata.*

class CoroutiueLivedataActivity : AppCompatActivity() {
    val model by viewModels<CoroutiueLivedataViewModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutiue_livedata)
        btn_startasync.setOnClickListener {
            model.startAsyncWithSecond(3).observe(this){
                btn_startasync.text=it
            }
        }

        btn_startasync_emitsource.setOnClickListener {
            model.startAsyncEmitSource(3).observe(this){
               btn_startasync_emitsource.text=it
            }
        }
    }
}