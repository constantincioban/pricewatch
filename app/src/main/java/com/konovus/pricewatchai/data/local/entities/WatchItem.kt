package com.konovus.pricewatchai.data.local.entities

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "watch_items")
data class WatchItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
    val currentPrice: Double = 0.0,
    val targetPrice: Double = 0.0,
    val priceHistory: List<Double> = emptyList(),
    val isWatching: Boolean = false
) {
}