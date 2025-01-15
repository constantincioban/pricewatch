package com.konovus.pricewatchai.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.konovus.pricewatchai.data.local.entities.WatchItem

@Database(entities = [ WatchItem::class,], version = 1, exportSchema = false)
abstract class PWDatabase: RoomDatabase() {
    abstract val watchItemDao: WatchItemDao
}