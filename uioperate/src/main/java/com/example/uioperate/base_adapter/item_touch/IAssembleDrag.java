package com.example.uioperate.base_adapter.item_touch;

// 继承这个接口的话，就可以获得一个能力，使得item主动触发拖拽
public interface IAssembleDrag {
    void assembleDrag(OnStartDragListener onStartDragListener);
}
