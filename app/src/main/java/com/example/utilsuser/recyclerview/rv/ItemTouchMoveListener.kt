package com.example.utilsuser.recyclerview.rv

interface ItemTouchMoveListener {
    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean

    fun onItemRemove(position: Int) : Boolean
}