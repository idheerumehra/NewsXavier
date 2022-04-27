package com.dheeraj.newsxavier.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.android.volley.Response
import com.dheeraj.newsxavier.model.NewsItem
import com.dheeraj.newsxavier.webservice.NewsService
import com.dheeraj.newsxavier.webservice.RetrofitInstance

class ArticleProvider {
    private val retService = RetrofitInstance.getRetrofitInstance().create(
        NewsService::class.java)

    private val responseLiveDataLiveData: LiveData<Response<NewsItem>> = liveData {
        val response = retService.getNews()
        emit(response)
    }

    var responseCategoryLiveData: MutableLiveData<Response<NewsItem>> = responseLiveDataLiveData as MutableLiveData<Response<NewsItem>>

    suspend fun getNews(): LiveData<Response<NewsItem>> = responseLiveDataLiveData

    suspend fun getNews(category: String):Response<NewsItem> {

        return when(category){

            "Technology" ->  retService.getNewsOnTech()

            "General" ->  retService.getNewsOnGeneral()

            "Business" ->  retService.getNewsOnBusiness()

            "Top Headlines" ->  retService.getNews()

            "Health" -> retService.getNewsOnHealth()

            "Entertainment" -> retService.getNewsOnEntertainment()

            "Sports" -> retService.getNewsOnSports()

            "Science" -> retService.getNewsOnScience()

            else ->  retService.getNews()
        }


    }
}