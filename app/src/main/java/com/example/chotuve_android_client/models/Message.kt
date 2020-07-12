package com.example.chotuve_android_client.models

data class Message (
    val fromId : String,
    val toId : String,
    val text : String,
    val timestamp: String
)