package com.ananananzhuo.livedatademo.globallivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ananananzhuo.livedatademo.R
import kotlinx.android.synthetic.main.activity_notify_global_data_with_livedata.*

class NotifyGlobalDataWithLivedataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify_global_data_with_livedata)
        btn_toFragmentA.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl_container, FragmentA()).commit()
        }
        btn_toFragmentB.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl_container, FragmentB()).commit()
        }
    }
}