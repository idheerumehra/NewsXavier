package com.dheeraj.newsxavier

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData

import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dheeraj.newsxavier.base.BaseActivity
import com.dheeraj.newsxavier.databinding.ActivityBookmarksBinding
import com.dheeraj.newsxavier.localdata.Bookmarks
import com.dheeraj.newsxavier.model.Article
import com.dheeraj.newsxavier.ui.ArticleDetailsActivity
import com.dheeraj.newsxavier.ui.adapter.NewsListAdapter
import com.dheeraj.newsxavier.ui.adapter.NewsTabAdapter
import com.dheeraj.newsxavier.ui.main.MainViewModel
import com.dheeraj.newsxavier.util.ObjectSerializer
import com.google.android.material.snackbar.Snackbar

class BookmarksActivity : BaseActivity<ActivityBookmarksBinding, MainViewModel, BookmarksActivity>() {

    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var newsTabAdapter: NewsTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        defineLayout()
    }


    private fun defineLayout() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setTheme(R.style.Theme_NewsX)
        setUpRecyclerView()
        binding.backImageButton.setOnClickListener {
            onBackPressed()
        }
    }

    fun setUpRecyclerView() {

        initAdapter()
        displayArticles()
    }


    private fun displayArticles() {
        viewModel.bookmarkList.observe(this, Observer { bookmarks: List<Bookmarks> ->
            val articles: ArrayList<Article> = ArrayList()
            for (bookmark in bookmarks) {
                articles.add(bookmark.article!!)
            }
            if (binding.recyclerView.adapter is NewsTabAdapter)
                newsTabAdapter.setArticleList(articles)
            else
                newsListAdapter.setArticleList(articles)
        })


    }

    private fun deleteBookmark(article: Article) {

        showAlertDialog("Delete?",
            "Deleting will lead to permanent removal of this bookmark from bookmarks",
            "Cancel",
            "Delete",
            { dialog: Dialog ->

                for (bookmark in viewModel.bookmarkList.value!!) {

                    if (bookmark.article == article) {

                        dialog.dismiss()

                        viewModel.deleteABookmark(bookmark)
                        Snackbar.make(
                            binding.root,
                            "Bookmark deleted successfully",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("Undo") {
                                viewModel.addABookmark(bookmark.id, bookmark.article)
                            }.setActionTextColor(resources.getColor(R.color.colorRed)).show()

                        break
                    }

                }
            }, { dialog: Dialog ->
                dialog.dismiss()
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bookmark_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.clear_all_bookmarks -> {
                showAlertDialog("Clear All?",
                    "Clearing all will lead to permanent removal of all bookmark from bookmarks." +
                            "This action can't undo",
                    "Cancel",
                    "Clear All",
                    { dialog: Dialog ->
                        dialog.dismiss()
                        viewModel.clearAllBookmarks()
                        Snackbar.make(
                            binding.root,
                            "All Bookmarks cleared sucessfully",
                            Snackbar.LENGTH_SHORT
                        ).show()


                    }
                    , { dialog: Dialog ->
                        dialog.dismiss()
                    })
            }
        }

        return true
    }


    private fun showAlertDialog(
        titleText: String,
        bodyText: String,
        negativeButtonText: String,
        positiveButtonText: String,
        positiveButtonAction: (Dialog) -> Unit,
        negativeButtonAction: (Dialog) -> Unit
    ) {

        val dialog = Dialog(this@BookmarksActivity)
        dialog.setContentView(R.layout.dialog_delete)

        dialog.findViewById<TextView>(R.id.title_delete_dialog).setText(titleText)
        dialog.findViewById<TextView>(R.id.subTitle_delete_dialog).setText(bodyText)
        dialog.findViewById<TextView>(R.id.delete_button_dialog).setText(positiveButtonText)
        dialog.findViewById<TextView>(R.id.cancel_button_dialog).setText(negativeButtonText)

        dialog.findViewById<Button>(R.id.delete_button_dialog).setOnClickListener {
            positiveButtonAction(dialog)
        }

        dialog.findViewById<Button>(R.id.cancel_button_dialog).setOnClickListener {
            negativeButtonAction(dialog)
        }

        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }


    private fun goToArticleDetailActivity(article: Article, imageView: ImageView) {
        val intent = Intent(applicationContext, ArticleDetailsActivity::class.java)
        intent.putExtra("article", ObjectSerializer.serialize(article))
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            "article_image"
        )
        intent.putExtra("fab_visiblity", View.GONE)
        startActivity(intent, activityOptions.toBundle())
    }


    override fun getContext(): Context = this

    override fun getViewBinding(): ActivityBookmarksBinding =
        ActivityBookmarksBinding.inflate(layoutInflater)

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewModelStoreOwner(): BookmarksActivity = this

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun initAdapter(){
        newsListAdapter =   NewsListAdapter({ article: Article, imageView: ImageView ->
            goToArticleDetailActivity(article, imageView)
        }
            , { article ->
                deleteBookmark(article)
            })

        newsTabAdapter =  NewsTabAdapter({ article: Article, imageView: ImageView ->
            goToArticleDetailActivity(article, imageView)
        }
            , { article ->
                deleteBookmark(article)
            })



        viewModel.appPrefrences.viewtype.asLiveData().observe(this, Observer {viewType ->
            if (viewType.equals("List"))
                binding.recyclerView.adapter = newsListAdapter
            else
                binding.recyclerView.adapter = newsTabAdapter

        })

        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

    }

}