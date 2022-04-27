package com.dheeraj.newsxavier.model


data class NewsItem(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)