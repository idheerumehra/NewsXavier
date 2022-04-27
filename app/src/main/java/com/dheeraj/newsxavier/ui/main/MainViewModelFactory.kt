package com.dheeraj.newsxavier.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dheeraj.newsxavier.preferences.AppPreferences
import com.dheeraj.newsxavier.localdata.BookmarksRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val repository: BookmarksRepository, private val preferences : AppPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository, preferences) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}
