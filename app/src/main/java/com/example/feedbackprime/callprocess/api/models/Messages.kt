package com.example.feedbackprime.callprocess.api.models

data class Messages(
    val id: String,
    val text: String,
    val from: From,
    val startTime: String,
    val endTime: String,
    val conversationId: String,
    val phrases: ArrayList<String>,
    val sentiment: Sentiment
)
