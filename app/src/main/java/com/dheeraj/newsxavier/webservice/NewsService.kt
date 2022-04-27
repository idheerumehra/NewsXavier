package com.dheeraj.newsxavier.webservice

import com.android.volley.Response
import com.dheeraj.newsxavier.model.NewsItem
import retrofit2.http.GET

interface NewsService {
    @GET("/v2/top-headlines?country=in&apiKey=")
    suspend fun getNews(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=business&apiKey=")
    suspend fun getNewsOnBusiness(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=general&apiKey=")
    suspend fun getNewsOnGeneral(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=health&apiKey=")
    suspend fun getNewsOnHealth(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=technology&apiKey=")
    suspend fun getNewsOnTech(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=science&apiKey=")
    suspend fun getNewsOnScience(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=entertainment&apiKey=")
    suspend fun getNewsOnEntertainment(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=sports&apiKey=")
    suspend fun getNewsOnSports(): Response<NewsItem>
}