package com.example.alpha.data.api

data class InventoryResult(
    val laboratoryId: Int,
    val elementList: List<com.example.alpha.data.api.Element>,
    val missingElement: List<com.example.alpha.data.api.Element>,
    val elementFromOtherLabs: List<com.example.alpha.data.api.Element>
)