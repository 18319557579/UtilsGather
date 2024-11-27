package com.example.uioperate.event_bus

// 规范化，所有消息都要实现该接口
interface IMessageEvent

class SimpleEvent : IMessageEvent

class SimpleOtherEvent : IMessageEvent

class TestStickyEvent : IMessageEvent