package com.tokyonth.module_common.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tokyonth.module_common.Constants

@Entity(tableName = Constants.DATABASE_TABLE_NAME)
data class OcrRecent(
    @PrimaryKey(autoGenerate = true)
    var number: Long = 0,
    var color: Int,
    var content: String
)
