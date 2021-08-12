package com.vishal.videop.ui.home.di

import android.content.Context
import com.vishal.videop.ui.home.data.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {
    @Provides
    fun providesRepository(@ApplicationContext context: Context) = HomeRepository(context)

}