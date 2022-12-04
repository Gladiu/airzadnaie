package com.example.iot_lab6.definitions.ledPanel

import kotlinx.serialization.Serializable

@Serializable
data class SetSingleLedPanelRequest(
    val x: Int,
    val y: Int,
    val color: String
)

// mozliwe ze bedzie trzeba w celu optymalizacji zrobic mozliwosc wysylu kilku (malo prawdopodobne)
