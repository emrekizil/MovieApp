package com.emrekizil.movieapp.data.source.local

import kotlinx.coroutines.flow.Flow

interface LayoutPreferenceSource {
    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}