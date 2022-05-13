package com.rahilkarim.trootechtask.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rahilkarim.trootechtask.ui.menu.db_model.Category
import com.rahilkarim.trootechtask.ui.menu.db_model.Menu
import com.rahilkarim.trootechtask.ui.menu.model.Categoria
import com.rahilkarim.trootechtask.ui.menu.model.MenuModel
import com.rahilkarim.trootechtask.ui.store.model.ApiErrorMessage
import com.rahilkarim.trootechtask.util.Constant.response_error
import com.rahilkarim.trootechtask.util.GlobalClass
import com.rahilkarim.trootechtask.util.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(private val repository: Repository,
                    private val globalClass: GlobalClass
): ViewModel() {

    var tag = "MenuViewModel"

    private val _list: MutableLiveData<MenuModel> = MutableLiveData()
    val list: LiveData<MenuModel>
    get() = _list

    val errorRes = MutableLiveData<String>()

    init {
//        deleteTable()
    }

    fun getMenuList(r: String, headersMap: Map<String, String>) {

        val categoryArrayList = arrayListOf<Categoria>()

        try {

            viewModelScope.launch {

                val response = repository.getMenuList(r, headersMap)
                if(response.isSuccessful) {

                    response.body()?.let { res ->

                        _list.postValue(res)
//                        globalClass.log(tag,res.toString())
                        globalClass.log(tag,"menuSize: ${res.data.size}")

                        for(c in 0 until res.data.size) {
                            val dataModel = res.data[c]
                            val categoryModel = dataModel.categoria
                            val catId = categoryModel.idcategoriamenu

                            if(categoryArrayList.isNotEmpty()) {

                                if(checkCategoryExist(categoryArrayList, catId)) {
                                    categoryArrayList.add(categoryModel)
                                    addCategory(Category(
                                        categoryId = categoryModel.idcategoriamenu,
                                        categoryName = categoryModel.nombremenu,
                                        APIKEY = "${headersMap["APIKEY"]}"))
                                }
                            }
                            else {
                                categoryArrayList.add(categoryModel)
                                addCategory(Category(
                                    categoryId = categoryModel.idcategoriamenu,
                                    categoryName = categoryModel.nombremenu,
                                    APIKEY = "${headersMap["APIKEY"]}"))
                            }
                        }

//                        globalClass.log(tag,"catArrayList: ${categoryArrayList.size}")

                        categoryArrayList.forEach { model->
                            for(d in 0 until res.data.size) {
                                val dataModel = res.data[d]
                                val categoryModel = dataModel.categoria
                                if(categoryModel.idcategoriamenu == model.idcategoriamenu) {
/*
                                    globalClass.log(tag,
                                        "catId: ${categoryModel.idcategoriamenu}, " +
                                                "catName: ${categoryModel.nombremenu}, " +
                                                "itemId: ${dataModel.idmenu}, " +
                                                "itemName: ${dataModel.nombre}, " +
                                                "APIKEY : ${headersMap["APIKEY"]}"
                                    )
*/
                                    addMenu(
                                        Menu(
                                            categoryId = categoryModel.idcategoriamenu,
                                            categoryName = categoryModel.nombremenu,
                                            itemId = dataModel.idmenu,
                                            itemName = dataModel.nombre,
                                            APIKEY = "${headersMap["APIKEY"]}",
                                            perPrice = dataModel.precioSugerido.toDouble()
                                        ))
                                }
                            }
                        }

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

    private fun checkCategoryExist(catArrayList: ArrayList<Categoria>, catId: String): Boolean {
        for(i in 0 until catArrayList.size) {
            val categoryModel2 = catArrayList[i]
            if(catId == categoryModel2.idcategoriamenu) {
                return false
            }
        }

        return true
    }

    //todo category
    private fun addCategory(model: Category) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(model)
        }
    }

    fun getCategoryList(APIKEY: String):LiveData<List<Category>> {
        return repository.getCategoryList(APIKEY)
    }

    private fun deleteTable() {

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTable()
        }
    }

    fun checkDataAvailable(APIKEY: String): Int{
        return repository.checkDataAvailable(APIKEY)
    }


    //todo menu
    private fun addMenu(model: Menu) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addMenu(model)
        }
    }

    fun updateMenu(model: Menu) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMenu(model)
        }
    }

    fun getListByCatId(categoryId: String, APIKEY: String):List<Menu> {
        return repository.getListByCatId(categoryId, APIKEY)
    }

}