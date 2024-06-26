package com.example.utilsuser.recyclerview.swipe

interface ItemSwipeListener {
    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean

    fun onItemRemove(position: Int) : Boolean
}