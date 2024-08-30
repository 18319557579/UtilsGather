package com.example.uioperate.custom_juejin_newki.thermometer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings

class TemperatureViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature_view)

        GuideSettings.setHorizontal(
            findViewById(R.id.rv_horizontal),
            arrayOf(
                GuideItemEntity("设置温度70") {
                    findViewById<TemperatureView>(R.id.temperature_view).setupTemperature(70f)
                },
                GuideItemEntity("") {

                },
                GuideItemEntity("") {

                },
                GuideItemEntity("") {

                },
            )
        )
    }
}