package com.example.uioperate.custom_scrolltext_canger

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uioperate.R

class LinesAdapter : RecyclerView.Adapter<LinesHolder>() {
    private val data = mutableListOf<LineWord>()

    private val highLightCount = 2  // 高亮行总数
    private val highLightStart = 0  // 高亮开始位置

    fun setData(data: List<LineWord>) {
        this.data.clear()
        this.data.addAll(data)
        data.forEachIndexed { index, lineWord ->
            lineWord.highLight = isHighLightLine(index, highLightStart)
        }
        notifyDataSetChanged()
    }

    fun setHighLightLines(highLightLineStart: Int) {
        data.forEachIndexed { index, lines ->
            lines.highLight = isHighLightLine(index, highLightLineStart)
        }
        notifyDataSetChanged()
    }

    /**
     * 判断是不是高亮行
     */
    private fun isHighLightLine(index: Int, highLightLineStart: Int): Boolean {
        for (i in 0 until highLightCount) {
            if (highLightLineStart + i == index - highLightStart) {
                return true
            }
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_line_item, parent, false)
        return LinesHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LinesHolder, position: Int) {
        holder.bindData(data[position])
    }
}

class LinesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val linesTv = itemView.findViewById<TextView>(R.id.line_tv)

    fun bindData(lineWord: LineWord) {
        linesTv.text = lineWord.word
        if (lineWord.highLight) {  // 如果是高亮的，使用粗体
            linesTv.setTextColor(Color.BLUE)
            linesTv.typeface = Typeface.DEFAULT_BOLD
        } else {
            linesTv.setTextColor(Color.BLACK)
            linesTv.typeface = Typeface.DEFAULT
        }
    }
}