package com.example.uioperate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uioperate.R
import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment

class BananaFragment(val desc: String) : LifecycleLogFragment() {

    lateinit var tvShow: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewCalled()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShow = view.findViewById(R.id.tv_show)

        refresh(desc)
    }


    fun refresh(content: String) {
        tvShow.text = content
    }
}