package com.dheeraj.newsxavier.ui.main

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Response
import com.dheeraj.newsxavier.R
import com.dheeraj.newsxavier.localdata.Bookmarks
import com.dheeraj.newsxavier.localdata.BookmarksRepository
import com.dheeraj.newsxavier.model.Article
import com.dheeraj.newsxavier.model.NewsItem
import com.dheeraj.newsxavier.preferences.AppPreferences
import com.dheeraj.newsxavier.repo.ArticleProvider
import com.dheeraj.newsxavier.util.Event
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: BookmarksRepository,
    private val prefrences: AppPreferences
) : ViewModel(), Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    @Bindable
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val category: MutableLiveData<String> = MutableLiveData("Top Headlines")
    private val message: MutableLiveData<Event<String>> = MutableLiveData()

    val errorMessage
        get() = message

    var responseLiveData: MutableLiveData<Response<NewsItem>?> = MutableLiveData()

    var bookmarkList: LiveData<List<Bookmarks>> = repository.bookmarks

    val appPrefrences: AppPreferences
        get() = prefrences


    fun refreshResponse() {

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = ArticleProvider().getNews(category.value!!)
                if (response.isSuccess) {
                    responseLiveData.postValue(response)
                } else {
                    responseLiveData.postValue(null)
                    message.value = Event(response.error.toString())
                }
            } catch (e: Exception) {
                responseLiveData.postValue(null)
                message.value = Event(e.toString())
            }

            isLoading.value = false
        }

    }

    fun changeViewType(id: Int) {

        when (id) {
            R.id.radio_button1 -> {
                viewModelScope.launch {
                    prefrences.saveViewType("List")
                }
            }
            R.id.radio_button2 -> {
                viewModelScope.launch {
                    prefrences.saveViewType("Tab")
                }
            }
        }

        refreshResponse()
    }

    fun addABookmark(id: Int = 0, article: Article) {

        viewModelScope.launch {
            val bookMark = Bookmarks(id, article)
            repository.insertArticleIntoBookmarks(bookMark)
        }

    }

    fun deleteABookmark(bookmark: Bookmarks) {
        viewModelScope.launch {
            repository.deleteArticlefromBookmarks(bookmark)
        }
    }

    fun clearAllBookmarks() {
        viewModelScope.launch {
            repository.deleteALlArticleFromBookmarks()
        }
    }
}
