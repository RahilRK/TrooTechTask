package com.rahilkarim.trootechtask.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.trootechtask.ui.menu.db_model.Menu


@Dao
interface MenuDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMenu(model: Menu)

    @Update
    suspend fun updateMenu(model: Menu)

    @Query("select count(id) as count from menu where itemId = :itemId AND APIKEY = :APIKEY")
    fun checkItemExist(itemId: String, APIKEY: String) :Int

    @Query("select count(id) as count from menu where APIKEY = :APIKEY")
    fun checkDataAvailable(APIKEY: String) :Int

    @Query("select * from menu where categoryId = :categoryId AND APIKEY = :APIKEY")
    fun getListByCatId(categoryId: String, APIKEY: String) :List<Menu>
}