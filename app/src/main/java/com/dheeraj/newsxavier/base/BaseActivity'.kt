package com.dheeraj.newsxavier.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.dheeraj.newsxavier.localdata.BookmarksDatabase
import com.dheeraj.newsxavier.localdata.BookmarksRepository
import com.dheeraj.newsxavier.preferences.AppPreferences
import com.dheeraj.newsxavier.ui.main.MainViewModelFactory

abstract class BaseActivity<binding : ViewDataBinding, viewModel : ViewModel, viewModelstoreOwner : ViewModelStoreOwner>() :
    AppCompatActivity() {

    abstract fun getViewBinding(): binding
    abstract fun getViewModel(): Class<viewModel>
    abstract fun getViewModelStoreOwner(): viewModelstoreOwner
    abstract fun getContext(): Context

    protected lateinit var binding: binding

    protected lateinit var viewModel: viewModel

    protected lateinit var prefrences: AppPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        val database: BookmarksDatabase =
            BookmarksDatabase.getInstance(context = applicationContext)
        val dao = database.dao
        val repository = BookmarksRepository(dao)

        prefrences = AppPreferences(this)

        val factory = MainViewModelFactory(repository, prefrences)
        viewModel = ViewModelProvider(getViewModelStoreOwner(), factory)[getViewModel()]
    }
}