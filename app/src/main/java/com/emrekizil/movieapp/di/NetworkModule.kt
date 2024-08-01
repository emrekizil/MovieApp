package com.emrekizil.movieapp.di

import com.emrekizil.movieapp.data.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}

