package com.example.uioperate.base_adapter.add_head_foot

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R
import com.example.utilsgather.ui.toast.ToastManager
import java.util.Random

class AddHeadFootActivity : AppCompatActivity() {
    lateinit var mAdapter: AddHeadFootAdapter
    val arrayList = listOf("娜扎", "小龙女", "迪丽热巴")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_head_foot)

        findViewById<RecyclerView>(R.id.recyclerView).adapter = AddHeadFootAdapter().apply {
            dataList = arrayList
            setOnItemClickListener { item, _ ->
                ToastManager.showToast(item)
            }
            mAdapter = this
        }

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val headLayout = LayoutInflater.from(this).inflate(R.layout.layout_head, null)
            val textView = headLayout.findViewById<TextView>(R.id.head_txt)
            val red = Random().nextInt(255)
            val green = Random().nextInt(255)
            val blue = Random().nextInt(255)
            textView.setBackgroundColor(Color.rgb(red, green, blue))
            mAdapter.addHeadView(headLayout)
        }
        findViewById<Button>(R.id.btn_add_2).setOnClickListener {

        }
    }
}