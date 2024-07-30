package com.example.uioperate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment

const val ARG_PARAM_DESC = "desc"

class BananaFragment : LifecycleLogFragment() {
    //由于Fragment不应该重写构造函数，且在onCreate中拿参数担心晚了，会有回调打印错过tag。因此使用这种方式将tag传入
    fun setFragmentTag(tag: String) {
        setTag(tag)
    }

    lateinit var desc: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            desc = it.getString(ARG_PARAM_DESC)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewCalled()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic_load, container, false)
    }

    lateinit var tvShow: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShow = view.findViewById(R.id.tv_show)

        refresh(desc)
    }


    fun refresh(content: String) {
        tvShow.text = content
    }
}