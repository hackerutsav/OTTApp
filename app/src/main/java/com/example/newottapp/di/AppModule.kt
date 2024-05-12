package com.example.newottapp.di

import com.example.newottapp.Repository.MovieRepository
import com.example.newottapp.Repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository =MovieRepositoryImpl()
}