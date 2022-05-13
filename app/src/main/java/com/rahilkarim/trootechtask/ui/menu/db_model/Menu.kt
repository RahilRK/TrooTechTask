package com.rahilkarim.trootechtask.ui.menu.db_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
data class Menu(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    @ColumnInfo(name = "categoryId", defaultValue = "")
    val categoryId: String = "",
    @ColumnInfo(name = "categoryName", defaultValue = "")
    val categoryName: String = "",
    @ColumnInfo(name = "itemId", defaultValue = "")
    val itemId: String = "",
    @ColumnInfo(name = "itemName", defaultValue = "")
    val itemName: String = "",
    @ColumnInfo(name = "APIKEY", defaultValue = "")
    val APIKEY: String = "",
    @ColumnInfo(name = "count", defaultValue = "1")
    var count: Int = 1,
    @ColumnInfo(name = "perPrice", defaultValue = "0")
    var perPrice: Double = 0.0,
    @ColumnInfo(name = "totalPrice", defaultValue = "0")
    var totalPrice: Double = 0.0
)