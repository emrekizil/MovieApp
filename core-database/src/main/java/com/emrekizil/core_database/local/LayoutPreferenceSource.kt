package com.emrekizil.core_database.local

import kotlinx.coroutines.flow.Flow

interface LayoutPreferenceSource {
    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}