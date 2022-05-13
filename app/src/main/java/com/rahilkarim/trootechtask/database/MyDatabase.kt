package com.rahilkarim.trootechtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahilkarim.trootechtask.R
import com.rahilkarim.trootechtask.database.dao.CategoryDAO
import com.rahilkarim.trootechtask.database.dao.MenuDAO
import com.rahilkarim.trootechtask.ui.menu.db_model.Category
import com.rahilkarim.trootechtask.ui.menu.db_model.Menu

@Database(
    entities =
    [
        Category::class,
        Menu::class,
    ], version = 1
)
abstract class MyDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            context.resources.getString(R.string.app_name)
        ).allowMainThreadQueries()
            .build()
    }

    abstract fun categoryDAO(): CategoryDAO
    abstract fun menuDAO(): MenuDAO
}