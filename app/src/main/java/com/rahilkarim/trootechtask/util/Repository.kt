package com.rahilkarim.trootechtask.util

import androidx.lifecycle.LiveData
import com.rahilkarim.trootechtask.database.MyDatabase
import com.rahilkarim.trootechtask.ui.menu.db_model.Category
import com.rahilkarim.trootechtask.ui.menu.db_model.Menu
import com.rk.movies.network.RetrofitClient

class Repository(private val globalClass: GlobalClass,
                 private val myDatabase: MyDatabase
) {

    suspend fun getStoreList(r: String) =
        RetrofitClient.apiCall.getStoreList(
            r
        )

    suspend fun getMenuList(r: String, headersMap: Map<String, String>) =
        RetrofitClient.apiCall.getMenuList(
            headersMap,
            r
        )

    suspend fun addCategory(model: Category) {

        myDatabase.categoryDAO().addCategory(model)
    }

    suspend fun deleteTable() {

        myDatabase.categoryDAO().deleteTable()
    }

    fun getCategoryList(APIKEY: String):LiveData<List<Category>> {
        return myDatabase.categoryDAO().getCategoryList(APIKEY)
    }

    //todo Menu
    fun checkDataAvailable(APIKEY: String):Int {
        return myDatabase.menuDAO().checkDataAvailable(APIKEY)
    }

    fun checkItemExist(itemId: String, APIKEY: String):Int {
        return myDatabase.menuDAO().checkItemExist(itemId, APIKEY)
    }

    suspend fun addMenu(model: Menu) {

        myDatabase.menuDAO().addMenu(model)
    }

    suspend fun updateMenu(model: Menu) {

        myDatabase.menuDAO().updateMenu(model)
    }

    fun getListByCatId(categoryId: String, APIKEY: String):List<Menu> {
        return myDatabase.menuDAO().getListByCatId(categoryId, APIKEY)
    }
}