package com.anonlatte.natgeo.data.di

import com.anonlatte.natgeo.data.repository.LocalRepository
import com.anonlatte.natgeo.data.repository.LocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindsLocalRepository(repository: LocalRepositoryImpl): LocalRepository
}