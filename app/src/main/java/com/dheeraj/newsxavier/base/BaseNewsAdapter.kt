package com.dheeraj.newsxavier.base

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dheeraj.newsxavier.model.Article

abstract class BaseNewsAdapter<VB : ViewDataBinding>(
    private val clickListener: (Article, ImageView) -> Unit,
    private val longClickListener: (Article) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<VB>>() {


    val articles: ArrayList<Article> = ArrayList()

    override fun getItemCount(): Int = articles.size


    fun setArticleList(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        holder.bind(articles[position], clickListener, longClickListener)
    }
}