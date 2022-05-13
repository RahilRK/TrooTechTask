package com.rahilkarim.trootechtask.ui.menu.db_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rahilkarim.trootechtask.util.Constant

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    @ColumnInfo(name = "categoryId", defaultValue = "")
    var categoryId: String = "",
    @ColumnInfo(name = "categoryName", defaultValue = "")
    var categoryName: String = "",
    @ColumnInfo(name = "APIKEY", defaultValue = "")
    var APIKEY: String = "",
    @Ignore
    var menuList: List<Menu> = emptyList(),
    @Ignore
    var type: Int = Constant.PARENT,
    @Ignore
    var isExpanded:Boolean = true
) /*{
    constructor(id:Long, categoryId:String, categoryName:String, APIKEY:String)
            : this(id, categoryId, categoryName,APIKEY,0) }*/