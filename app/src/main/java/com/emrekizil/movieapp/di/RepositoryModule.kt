package com.emrekizil.movieapp.di

import com.emrekizil.movieapp.data.repository.MovieRepository
import com.emrekizil.movieapp.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl):MovieRepository
}