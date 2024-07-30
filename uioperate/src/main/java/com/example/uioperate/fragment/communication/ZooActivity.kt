package com.example.uioperate.fragment.communication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uioperate.R

class ZooActivity : AppCompatActivity(), EatDemand, DrinkDemand, MonkeyCallFish{
    lateinit var tvBoardContent: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communication)
        tvBoardContent = findViewById(R.id.tv_board_cotent)

        findViewById<Button>(R.id.btn_start_acrobatics).setOnClickListener {
            (supportFragmentManager.findFragmentById(R.id.fragment_monkey) as MonkeyWork).startAcrobatics()
        }
        findViewById<Button>(R.id.btn_start_aquashow).setOnClickListener {
            (supportFragmentManager.findFragmentById(R.id.fragment_fish) as FishWork).startQquashow()
        }
    }

    override fun requestEat() {
        tvBoardContent.text = "动物园管理人员来喂食了"
    }

    override fun requestDrink() {
        tvBoardContent.text = "动物园管理人员来给水喝了"
    }

    override fun invoke() {
        (supportFragmentManager.findFragmentById(R.id.fragment_fish) as FishWork).playWithMe()
    }
}