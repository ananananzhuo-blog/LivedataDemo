package com.ananananzhuo.livedatademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ananananzhuo.livedatademo.countdown.CountDownActivity
import com.ananananzhuo.livedatademo.globallivedata.NotifyGlobalDataWithLivedataActivity
import com.ananananzhuo.livedatademo.livedatawithcoroutiue.CoroutiueLivedataActivity
import com.ananananzhuo.livedatademo.meditorlivedata.MeditorLivedataActivity
import com.ananananzhuo.livedatademo.transformationmap.TransMapActivity
import com.ananananzhuo.livedatademo.transformationswitchmap.TransSwitchMapActivity
import com.ananananzhuo.livedatademo.updatasimdata.UpdateSimDataActivity
import kotlinx.android.synthetic.main.activity_main.*
fun logEE(msg:String){
    Log.e("安安安安卓",msg)
}
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

        btn_transformation_map.setOnClickListener {
            startActivity(Intent(this,TransMapActivity::class.java))
        }
        btn_transformation_switchmap.setOnClickListener {
            startActivity(Intent(this,TransSwitchMapActivity::class.java))
        }
        btn_meditorlivedata.setOnClickListener {
            startActivity(Intent(this,MeditorLivedataActivity::class.java))
        }
        btn_livedata_with_coroutiue.setOnClickListener {
            startActivity(Intent(this,CoroutiueLivedataActivity::class.java))
        }
        btn_background_livedata.setOnClickListener {
            startActivity(Intent(this,BackgroundLivedataActivity::class.java))
        }
        btn_updatesimdata.setOnClickListener {
            startActivity(Intent(this,UpdateSimDataActivity::class.java))
        }
    }
}