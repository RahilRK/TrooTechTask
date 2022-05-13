package com.rahilkarim.trootechtask.util

import android.app.Application
import com.rahilkarim.trootechtask.database.MyDatabase

class Application: Application() {

    lateinit var repository: Repository
    lateinit var globalClass: GlobalClass
    lateinit var myDatabase: MyDatabase

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {

        globalClass = GlobalClass.getInstance(applicationContext)
        myDatabase = MyDatabase.getInstance(applicationContext)
        repository = Repository(globalClass, myDatabase)
    }
}