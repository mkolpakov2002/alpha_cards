package com.example.alpha.data.api

data class QualityComment(
    val id: Int,
    val requestId: Int,
    val equipmentId: Int,
    val comment: String,
    val created: String,
    val updated: String
)