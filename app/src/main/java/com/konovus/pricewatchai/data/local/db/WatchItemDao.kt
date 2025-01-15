package com.konovus.pricewatchai.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.konovus.pricewatchai.data.local.entities.WatchItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchItemDao {

    @Upsert
    suspend fun upsertWatchItem(watchItem: WatchItem)

    @Query("select * from watch_items")
    fun getWatchItems(): Flow<List<WatchItem>>

    @Query("select * from watch_items where :id = id")
    suspend fun getWatchItemById(id: Int): WatchItem?

    @Query("delete from watch_items where :id = id")
    suspend fun deleteWatchItemById(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWatchItems(watchItems: List<WatchItem>)
}