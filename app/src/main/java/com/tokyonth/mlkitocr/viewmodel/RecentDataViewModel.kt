package com.tokyonth.mlkitocr.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.tokyonth.module_common.ColorTables
import com.tokyonth.module_common.Constants
import com.tokyonth.module_common.db.OcrRecent
import com.tokyonth.module_common.db.SQLDatabase
import kotlinx.coroutines.launch

class RecentDataViewModel(application: Application) : AndroidViewModel(application) {

    private var db: SQLDatabase = Room.databaseBuilder(
        getApplication(),
        SQLDatabase::class.java,
        Constants.DATABASE_TABLE_NAME
    ).build()

    private val _dataResultLiveData = MutableLiveData<MutableList<OcrRecent>>()

    val dataResultLiveData: MutableLiveData<MutableList<OcrRecent>> = _dataResultLiveData

    fun getAllData() {
        viewModelScope.launch {
            val allData = db.recentDao().queryAll()
            _dataResultLiveData.value = allData.toMutableList()
        }
    }

    fun insertData(content: String) {
        viewModelScope.launch {
            val recent = OcrRecent(0, randomColor(), content)
            db.recentDao().insert(recent)
        }
    }

    fun removeData(recent: OcrRecent) {
        viewModelScope.launch {
            db.recentDao().delete(recent)
        }
    }

    private fun randomColor(): Int {
        val colors = ColorTables.mdColors
        val random = 0 + (Math.random() * (colors.size - 1 - 0 + 1)).toInt()
        return Color.parseColor(colors[random]) and 0x40FFFFFF
    }

}
