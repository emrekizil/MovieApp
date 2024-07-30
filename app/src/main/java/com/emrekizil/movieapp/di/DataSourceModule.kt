package com.emrekizil.movieapp.di

import com.emrekizil.movieapp.data.source.remote.RemoteDataSource
import com.emrekizil.movieapp.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSource): RemoteDataSourceImpl
}