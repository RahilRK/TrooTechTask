package com.rahilkarim.trootechtask.ui.menu

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.trootechtask.ConnectionLiveData
import com.rahilkarim.trootechtask.R
import com.rahilkarim.trootechtask.adapter.CategoryAdapter
import com.rahilkarim.trootechtask.databinding.ActivityMainBinding
import com.rahilkarim.trootechtask.ui.menu.db_model.Category
import com.rahilkarim.trootechtask.ui.menu.db_model.Menu
import com.rahilkarim.trootechtask.util.Application
import com.rahilkarim.trootechtask.util.GlobalClass
import com.rahilkarim.trootechtask.util.Repository

class MenuActivity : AppCompatActivity() {

    private var tag = "MenuActivity"
    private var activity = this

    lateinit var binding: ActivityMainBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: MenuViewModel

    private var name = ""
    private var getApiValue = ""

    val recyclerView : RecyclerView get() = binding.recyclerView

    private var arrayList = arrayListOf<Category>()
    lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            title = intent.getStringExtra("name").toString()
        }

        init()
        checkDataAvailable()
    }

    private fun init() {
        globalClass = GlobalClass.getInstance(activity)
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, MenuViewModelFactory(repository, globalClass))
            .get(MenuViewModel::class.java)
        getApiValue = intent.getStringExtra("APIKEY").toString()
    }

    private fun checkDataAvailable() {

        if(viewModel.checkDataAvailable(getApiValue) == 0) {
            globalClass.log(tag,"callApi")
            callApi()
            observeData()
        }
        else {
            globalClass.log(tag,"callLocalData")
            observeData()
        }
    }

    private fun callApi() {
        globalClass.isInternetPresent().apply {
            if(!this) {
                globalClass.toastlong("No internet connection found")
            }
        }

        val connectionLiveData = ConnectionLiveData(activity)
        connectionLiveData.observe(this) { isConnected ->
            isConnected?.let {
                if(it) {
                    if(arrayList.isEmpty()) {
                        val map = mutableMapOf<String,String>()
                        map["APIKEY"] = getApiValue
                        viewModel.getMenuList("menu",map)
                    }
                }
                else {
                    globalClass.toastlong("No internet connection found")
                }
            }
        }
    }

    private fun observeData() {

        viewModel.getCategoryList(getApiValue).observe(this) { list ->

            if(list.isNotEmpty()) {

                for(model in list) {
                    model.menuList = viewModel.getListByCatId(model.categoryId, getApiValue)
//                    globalClass.log(tag, "menuList: ${model.menuList.size}")
                }
//                globalClass.log(tag,"categoryList: ${list.size}")
                arrayList = list as ArrayList<Category>
                setAdapter()
            }
        }
    }

    private fun setAdapter() {

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CategoryAdapter(activity, arrayList, object : CategoryAdapter.categoryListOnClick {
            override fun onClick(position: Int, model: Menu) {

                showCustomDialog(position, model)
            }
        })
        recyclerView.adapter = adapter
    }

    private fun showCustomDialog(position: Int, model: Menu) {

        var current_count = model.count
        val current_price: Double
        if(current_count == 1) {
            current_price = model.perPrice
        }
        else {
            current_price = model.totalPrice
        }

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialogue_layout)
        val quantity = dialog.findViewById(R.id.quantity) as TextView
        quantity.text = current_count.toString()

        val itemName = dialog.findViewById(R.id.itemName) as TextView
        itemName.text = model.itemName
        val itemPrice = dialog.findViewById(R.id.itemPrice) as TextView
        itemPrice.text = current_price.toString()

        val cancelbt = dialog.findViewById(R.id.cancelbt) as Button
        val confirmbt = dialog.findViewById(R.id.confirmbt) as Button

        val ivRemove = dialog.findViewById(R.id.ivRemove) as ImageView
        ivRemove.setOnClickListener {
            if(current_count > 1) {
                current_count--
                quantity.text = current_count.toString()

                val calPrice = current_count * model.perPrice
                itemPrice.text = "$calPrice"

                model.count = current_count
                model.totalPrice = calPrice
            }
        }
        val ivAdd = dialog.findViewById(R.id.ivAdd) as ImageView
        ivAdd.setOnClickListener {
            current_count++
            quantity.text = current_count.toString()

            val calPrice = current_count * model.perPrice
            itemPrice.text = "$calPrice"

            model.count = current_count
            model.totalPrice = calPrice
        }

        confirmbt.setOnClickListener {
            dialog.dismiss()
            viewModel.updateMenu(model)
        }

        cancelbt.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}