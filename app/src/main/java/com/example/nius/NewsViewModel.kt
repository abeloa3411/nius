package com.example.nius

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import kotlin.math.log

class NewsViewModek:ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    init {
        getTopNews()
    }


    fun getTopNews(){
        val myApiClient = NewsApiClient(Consonants.apiKey)

        val request = TopHeadlinesRequest.Builder().language("en").build()

        myApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    Log.i("News api response fail",throwable.localizedMessage)
                }
            }

        })


    }
}