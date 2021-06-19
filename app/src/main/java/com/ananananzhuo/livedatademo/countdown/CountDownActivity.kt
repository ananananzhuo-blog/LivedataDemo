package com.ananananzhuo.livedatademo.countdown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ananananzhuo.livedatademo.R
import kotlinx.android.synthetic.main.activity_count_down.*

class CountDownActivity : AppCompatActivity() {
    val countDownModel: CountDownModel by viewModels<CountDownModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        countDownModel.countDownLivedata.observe(this, object : Observer<String> {
            override fun onChanged(value: String?) {
                value?.let {
                    tv_countdown_remainsecond.text = it
                }
            }
        })
    }
}