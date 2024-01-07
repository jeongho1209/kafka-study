package com.example.kafkastudy.test2_slack

data class SlackReport(
    val userName: String,
    val reason: String,
    val category: String,
    val imageUrls: List<String>,
)
