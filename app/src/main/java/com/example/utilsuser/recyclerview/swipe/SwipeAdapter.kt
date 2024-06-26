package com.example.utilsuser.recyclerview.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.utilsuser.R
import java.util.Collections

class SwipeAdapter(val webList: MutableList<SwipeInfoBean>, val context: Context)
    : RecyclerView.Adapter<SwipeAdapter.ItemViewHolder>(), ItemSwipeListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_swipe, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val webInfoBean = webList[position]
        holder.tvItemWebTitle.text = webInfoBean.webTitle
    }

    override fun getItemCount(): Int {
        return webList.size
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivItemWebsite: ImageView
        val tvItemWebTitle: TextView
        val ivItemClose: ImageView
        val ivDelete: ImageView
        val tvDelete: TextView
        init {
            ivItemWebsite = view.findViewById(R.id.iv_item_website)
            tvItemWebTitle = view.findViewById(R.id.tv_item_web_title)
            ivItemClose = view.findViewById(R.id.iv_item_close)
            ivDelete = view.findViewById(R.id.iv_detele)
            tvDelete = view.findViewById(R.id.tv_detele)

            ivItemClose.setOnClickListener {
                onItemRemove(adapterPosition)
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(webList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemRemove(position: Int): Boolean {
        webList.removeAt(position)
        notifyItemRemoved(position)
        return true
    }
}