package com.rahilkarim.trootechtask.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.trootechtask.util.GlobalClass
import com.rahilkarim.trootechtask.util.Repository

class StoreViewModelFactory(private val repository: Repository,
                            private val globalClass: GlobalClass
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StoreViewModel(repository,globalClass) as T
    }
}