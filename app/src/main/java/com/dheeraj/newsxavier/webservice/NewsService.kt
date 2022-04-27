package com.dheeraj.newsxavier.webservice

import com.android.volley.Response
import com.dheeraj.newsxavier.model.NewsItem
import retrofit2.http.GET

interface NewsService {
    @GET("/v2/top-headlines?country=in&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNews(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=business&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnBusiness(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=general&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnGeneral(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=health&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnHealth(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=technology&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnTech(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=science&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnScience(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=entertainment&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnEntertainment(): Response<NewsItem>
    @GET("/v2/top-headlines?country=in&category=sports&apiKey=08a23b06363f47698aeb112fb7385390")
    suspend fun getNewsOnSports(): Response<NewsItem>
}
