package com.example.alpha.data.api

data class Report(
    val requestId: Int,
    val equipmentList: List<com.example.alpha.data.api.Equipment>,
    val missingEquipment: List<com.example.alpha.data.api.Equipment>,
    val equipmentFromOtherLabs: List<com.example.alpha.data.api.Equipment>,
    val timestamp: String
)