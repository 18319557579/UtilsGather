package com.example.uioperate.custom_scrolltext_canger

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.recyclerViewStyle
) : RecyclerView(context, attrs, defStyleAttr){

    init {
        val layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        setLayoutManager(layoutManager)

        adapter = LinesAdapter()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                (adapter as LinesAdapter).setHighLightLines(
                    // 第一个可以完全展示的item
                    layoutManager.findFirstCompletelyVisibleItemPosition()
                )
            }
        })
    }

    fun setStrData(data: List<String>) {
        (adapter as LinesAdapter).setData(data.map {
            LineWord(word = it, highLight = false)
        })
    }
}