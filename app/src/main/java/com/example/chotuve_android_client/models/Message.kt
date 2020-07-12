package com.example.chotuve_android_client.models

import java.sql.Timestamp

data class Message (
//    val id : String,
    val fromId : String,
    val toId : String,
    val text : String,
    val timestamp: String
)