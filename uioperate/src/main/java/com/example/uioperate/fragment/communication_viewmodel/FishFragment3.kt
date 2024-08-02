package com.example.uioperate.fragment.communication_viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment
import com.example.utilsgather.logcat.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FishFragment3 : LifecycleLogFragment() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(Zoo3ActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewCalled()
        return inflater.inflate(R.layout.fragment_fish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            viewModel.setActivityObserve("我要吃东西")
        }

        viewModel.fishObserve.observe(viewLifecycleOwner) { message ->
            LogUtil.d("鱼儿收到通知: $message")
        }
    }
}