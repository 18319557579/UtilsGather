package com.example.uioperate.fragment.communication_viewmodel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment
import com.example.utilsgather.logcat.LogUtil

class MonkeyFragment3 : LifecycleLogFragment(){
    //依赖：implementation 'androidx.fragment:fragment-ktx:1.8.2'
    private val viewMode: Zoo3ActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewCalled()
        return inflater.inflate(R.layout.fragment_monkey, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            viewMode.setActivityObserve("我要喝水")
        }
        view.findViewById<Button>(R.id.btn_call_fish).setOnClickListener {
            viewMode.setFishObserve(99999999999L)
        }

        viewMode.monkeyObserve.observe(viewLifecycleOwner) { message ->
            LogUtil.d("猴哥收到通知: $message")
        }
    }
}