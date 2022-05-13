package com.rahilkarim.trootechtask.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.trootechtask.ui.menu.db_model.Category

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(model: Category)

    @Query("select * from category where APIKEY = :APIKEY")
    fun getCategoryList(APIKEY: String) :LiveData<List<Category>>

    @Query("Delete from category")
    fun deleteTable()
}