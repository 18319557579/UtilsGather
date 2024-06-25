package com.example.utilsuser.recyclerview.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.utilsuser.R

class SlideDeleteAdapter(val webList: List<WebInfoBean>, val context: Context) : RecyclerView.Adapter<SlideDeleteAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_slide_delete, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val webInfoBean = webList[position]
        holder.tvItemWebTitle.text = webInfoBean.webTitle
    }

    override fun getItemCount(): Int {
        return webList.size
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivItemWebsite: ImageView
        val tvItemWebTitle: TextView
        val ivItemClose: ImageView
        init {
            ivItemWebsite = view.findViewById(R.id.iv_item_website)
            tvItemWebTitle = view.findViewById(R.id.tv_item_web_title)
            ivItemClose = view.findViewById(R.id.iv_item_close)
        }
    }
}