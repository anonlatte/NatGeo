package com.anonlatte.natgeo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anonlatte.natgeo.data.MainRepository
import com.anonlatte.natgeo.data.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    fun getNews(): LiveData<List<Article>> = liveData {
        val articlesResponse = mainRepository.getTopHeadlines()
        emit(articlesResponse.articles)
    }
}