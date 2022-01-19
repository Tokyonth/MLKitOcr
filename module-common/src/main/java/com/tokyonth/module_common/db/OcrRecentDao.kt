package com.tokyonth.module_common.db

import androidx.room.*
import com.tokyonth.module_common.Constants

@Dao
interface OcrRecentDao {

    @Query("SELECT * FROM ${Constants.DATABASE_TABLE_NAME}")
    suspend fun queryAll(): List<OcrRecent>

    @Insert
    suspend fun insert(vararg recent: OcrRecent): List<Long>

    @Delete
    suspend fun delete(recent: OcrRecent): Int

    @Update
    suspend fun update(recent: OcrRecent): Int

}
