package com.example.uioperate.fragment.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.uioperate.R


class FishFragment : Fragment(), FishWork {
    lateinit var tv_show: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            val eatDemand = activity as EatDemand
            eatDemand.requestEat()
        }
        tv_show = view.findViewById(R.id.tv_show)
    }

    override fun startQquashow() {
        tv_show.text = "开始水上表演"
    }

    override fun playWithMe() {
        tv_show.text = "有小动物要和我玩"
    }


}