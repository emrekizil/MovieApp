package com.emrekizil.core_database.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

const val DATASTORE_NAME = "movie_preferences"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): com.emrekizil.core_database.database.MovieDatabase {
        return Room.databaseBuilder(
            context,
            com.emrekizil.core_database.database.MovieDatabase::class.java,
            "movie_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideMovieDao(movieDatabase: com.emrekizil.core_database.database.MovieDatabase): com.emrekizil.core_database.database.MovieDao = movieDatabase.movieDao()

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext:Context) : DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {appContext.preferencesDataStoreFile(DATASTORE_NAME)}
        )
    }
}