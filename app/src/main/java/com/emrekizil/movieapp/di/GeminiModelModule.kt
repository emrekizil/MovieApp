package com.emrekizil.movieapp.di

import com.emrekizil.movieapp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeminiModelModule {

    @Provides
    @Singleton
    fun provideGeminiModel() : GenerativeModel {
        return GenerativeModel (
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }
}