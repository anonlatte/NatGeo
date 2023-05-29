package com.anonlatte.natgeo.ui.article.di

import com.anonlatte.natgeo.ui.article.interactor.ArticleInteractor
import com.anonlatte.natgeo.ui.article.interactor.ArticleInteractorImpl
import com.anonlatte.natgeo.ui.article.viewmodel.ArticleViewModel
import com.anonlatte.natgeo.ui.article.viewmodel.ArticleViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArticleModule {
    @Binds
    abstract fun bindsArticleViewModel(viewModel: ArticleViewModelImpl): ArticleViewModel

    @Binds
    abstract fun bindsArticleInteractor(interactor: ArticleInteractorImpl): ArticleInteractor
}