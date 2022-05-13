package com.rahilkarim.trootechtask.ui.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rahilkarim.trootechtask.ui.store.model.ApiErrorMessage
import com.rahilkarim.trootechtask.ui.store.model.StoreModel
import com.rahilkarim.trootechtask.util.Constant.response_error
import com.rahilkarim.trootechtask.util.GlobalClass
import com.rahilkarim.trootechtask.util.Repository
import kotlinx.coroutines.launch

class StoreViewModel(private val repository: Repository,
                     private val globalClass: GlobalClass
): ViewModel() {

    var tag = "StoreViewModel"

    private val _list: MutableLiveData<StoreModel> = MutableLiveData()
    val list: LiveData<StoreModel>
    get() = _list

    val errorRes = MutableLiveData<String>()

/*
    init {
        globalClass.isInternetPresent().apply {
            if(this) {
                getStoreList("configuraciones/franquicias")
            }
            else {
                globalClass.toastlong("No internet connection found")
            }
        }
    }
*/

    fun getStoreList(r: String) {

        try {

            viewModelScope.launch {

                val response = repository.getStoreList(r)
                if(response.isSuccessful) {

                    response.body()?.let { res ->

                        _list.postValue(res)
//                        globalClass.log(tag,res.toString())


                    }?:run {

                        val error = response_error
                        globalClass.log(tag,error)
                        errorRes.postValue(error)
                    }
                }
                else {

                    val gson = Gson()
                    val apiErrorModel: ApiErrorMessage = gson.fromJson(
                        response.errorBody()!!.charStream(),
                        ApiErrorMessage::class.java
                    )

                    val error = apiErrorModel.message
                    globalClass.log(tag,error)
                    errorRes.postValue(error)
                }
            }
        }
        catch (e: Exception) {

            val error = Log.getStackTraceString(e)
            globalClass.log(tag,error)
            errorRes.postValue(error)
        }
    }
}