package com.emrekizil.core_database.di

import com.emrekizil.core_database.local.LayoutPreferenceSource
import com.emrekizil.core_database.local.LayoutPreferenceSourceImpl
import com.emrekizil.core_database.local.LocalDataSource
import com.emrekizil.core_database.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalSourceModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLocalDataSource(localDataStoreImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindLayoutPreferenceSource(layoutPreferenceSourceImpl: LayoutPreferenceSourceImpl): LayoutPreferenceSource
}