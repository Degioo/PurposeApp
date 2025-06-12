package com.example.myapplication

data class Purpose(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val timestamp: Long = 0L,
    val days: List<String> = emptyList(),
    val latitude: Double? = null,
    val longitude: Double? = null,
    val sharedWith: List<String> = emptyList()
)
