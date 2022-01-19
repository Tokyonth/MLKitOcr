package com.tokyonth.module_common.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OcrRecent::class], version = 1)
abstract class SQLDatabase : RoomDatabase() {

    abstract fun recentDao(): OcrRecentDao

}
