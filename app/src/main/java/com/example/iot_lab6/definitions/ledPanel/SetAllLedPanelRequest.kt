package com.example.iot_lab6.definitions.ledPanel

import kotlinx.serialization.Serializable

@Serializable
data class SetAllLedPanelRequest(
    val R: Int,
    val G: Int,
    val B: Int
)
