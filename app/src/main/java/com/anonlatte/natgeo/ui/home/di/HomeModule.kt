package com.anonlatte.natgeo.ui.home.di

import com.anonlatte.natgeo.data.repository.MainRepository
import com.anonlatte.natgeo.data.repository.MainRepositoryImpl
import com.anonlatte.natgeo.ui.home.interactor.HomeInteractor
import com.anonlatte.natgeo.ui.home.interactor.HomeInteractorImpl
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModel
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindsHomeViewModel(viewModel: HomeViewModelImpl): HomeViewModel

    @Binds
    abstract fun bindsHomeInteractor(interactor: HomeInteractorImpl): HomeInteractor

    @Binds
    abstract fun bindsMainRepository(repository: MainRepositoryImpl): MainRepository
}