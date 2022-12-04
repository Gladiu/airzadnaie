package com.example.iot_lab6.definitions.Mwykresy

@kotlinx.serialization.Serializable
data class WykresRow (
    val name: String,
    val value: Double,
    val unit: String
)

