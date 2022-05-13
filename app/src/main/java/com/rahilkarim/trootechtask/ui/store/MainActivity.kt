package com.rahilkarim.trootechtask.ui.store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.trootechtask.ConnectionLiveData
import com.rahilkarim.trootechtask.R
import com.rahilkarim.trootechtask.adapter.StoreAdapter
import com.rahilkarim.trootechtask.databinding.ActivityMainBinding
import com.rahilkarim.trootechtask.ui.menu.MenuActivity
import com.rahilkarim.trootechtask.ui.store.model.Franquicia
import com.rahilkarim.trootechtask.ui.store.model.StoreModel
import com.rahilkarim.trootechtask.util.Application
import com.rahilkarim.trootechtask.util.GlobalClass
import com.rahilkarim.trootechtask.util.Repository

class MainActivity : AppCompatActivity() {

    private var tag = "MainActivity"
    private var activity = this

    lateinit var binding: ActivityMainBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: StoreViewModel

    val recyclerView : RecyclerView get() = binding.recyclerView

    private var arrayList = arrayListOf<Franquicia>()
    lateinit var adapter: StoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeData()

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
                        viewModel.getStoreList("configuraciones/franquicias")
                    }
                }
                else {
                    globalClass.toastlong("No internet connection found")
                }
            }
        }
    }

    private fun init() {
        globalClass = GlobalClass.getInstance(activity)
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, StoreViewModelFactory(repository, globalClass))
            .get(StoreViewModel::class.java)
    }

    private fun observeData() {

        viewModel.list.observe(this) { model ->

            if(model.franquicias.isNotEmpty()) {
                arrayList = model.franquicias as ArrayList<Franquicia>

//                globalClass.log(tag,"storeList: ${arrayList.size}")
                setAdapter()
            }
        }
    }

    private fun setAdapter() {

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = StoreAdapter(activity, arrayList, object : StoreAdapter.storeListOnClick {
            override fun onClick(position: Int, model: Franquicia) {
                val intent = Intent(activity, MenuActivity::class.java).apply {
                    putExtra("name",model.negocio)
                    putExtra("APIKEY",model.APIKEY)
                }
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
    }
}