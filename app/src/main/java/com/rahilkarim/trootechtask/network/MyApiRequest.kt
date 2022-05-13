package com.rk.movies.network

import com.rahilkarim.trootechtask.ui.menu.model.MenuModel
import com.rahilkarim.trootechtask.ui.store.StoreViewModel
import com.rahilkarim.trootechtask.ui.store.model.StoreModel
import retrofit2.Response
import retrofit2.http.*

interface MyApiRequest {

    @Headers("APIKEY: bd_suvlascentralpos")
    @GET("invuApiPos/index.php?")
    suspend fun getStoreList(
        @Query("r") r: String,
    ) : Response<StoreModel>

    @GET("invuApiPos/index.php?")
    suspend fun getMenuList(
        @HeaderMap headers: Map<String, String>,
        @Query("r") r: String,
    ) : Response<MenuModel>
}