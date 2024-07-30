package com.example.uioperate.fragment.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.uioperate.R


class MonkeyFragment : Fragment(), MonkeyWork {
    lateinit var tv_show: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monkey, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            val eatDemand = activity as DrinkDemand
            eatDemand.requestDrink()
        }
        view.findViewById<Button>(R.id.btn_call_fish).setOnClickListener {
            val monkeyCallFish = activity as MonkeyCallFish
            monkeyCallFish.invoke()
        }
        tv_show = view.findViewById(R.id.tv_show)
    }

    override fun startAcrobatics() {
        tv_show.text = "开始杂技表演"
    }
}