package com.example.uioperate.event_bus

// 规范化，所有消息都要实现该接口（可以通过监听IMessageEvent，来实现接收所有消息，因为EventBus说了超类可以接收到子类的消息，但我觉得没必要）
interface IMessageEvent

class SimpleEvent : IMessageEvent

class SimpleOtherEvent : IMessageEvent

class TestStickyEvent : IMessageEvent

class NormalStickyEvent : IMessageEvent