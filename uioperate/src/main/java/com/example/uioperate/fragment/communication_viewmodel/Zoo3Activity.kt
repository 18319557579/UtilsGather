package com.example.uioperate.fragment.communication_viewmodel

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R
import com.example.utilsgather.logcat.LogUtil

class Zoo3Activity : AppCompatActivity() {
//    val viewModel = ViewModelProvider(this).get(Zoo3ActivityViewModel::class.java)
    val viewModel: Zoo3ActivityViewModel by viewModels<Zoo3ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoo3)

        findViewById<Button>(R.id.btn_start_acrobatics).setOnClickListener {
            viewModel.setMonkeyObserve(123)
        }
        findViewById<Button>(R.id.btn_start_aquashow).setOnClickListener {
            viewModel.setFishObserve(8888888888L)
        }

        viewModel.activityObserve.observe(this) {
            LogUtil.d("动物园收到通知: $it")
        }
    }
}