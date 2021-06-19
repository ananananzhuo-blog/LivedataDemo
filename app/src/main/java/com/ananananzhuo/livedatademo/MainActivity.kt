package com.ananananzhuo.livedatademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ananananzhuo.livedatademo.countdown.CountDownActivity
import com.ananananzhuo.livedatademo.globallivedata.NotifyGlobalDataWithLivedataActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_tocountdown.setOnClickListener {
            startActivity(Intent(this, CountDownActivity::class.java))
        }
        btn_toglobaldatanotify.setOnClickListener {
            startActivity(Intent(this,NotifyGlobalDataWithLivedataActivity::class.java))
        }

    }
}