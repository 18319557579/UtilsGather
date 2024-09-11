package com.example.uioperate.custom_scrolltext_canger

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uioperate.R

class RecyclerViewScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_scroll)
        findViewById<ScrollRecyclerView>(R.id.scrollRecyclerView)
            .setStrData(TEXT.split("\n"))
    }

    companion object {
        private const val TEXT = "划一根火柴\n" +
                "将慵倦的夜点亮\n" +
                "吐出一缕烟\n" +
                "飘向半掩的窗\n" +
                "你纵身跃入酒杯\n" +
                "梦从此溺亡\n" +
                "心门上一把锁\n" +
                "钥匙在你手上\n" +
                "快将尘埃掸落\n" +
                "别将你眼眸弄脏\n" +
                "或许吧\n" +
                "谈笑中你早已淡忘\n" +
                "而我在颠沛中\n" +
                "已饱经一脸沧桑\n" +
                "思念需要时间\n" +
                "慢慢调养"
    }
}