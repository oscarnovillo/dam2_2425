package com.example.compose.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.compose.data.local.AppDatabase
import com.example.compose.data.local.CocheDao
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        )   .createFromAsset("database/coches.db")
            .fallbackToDestructiveMigration()
            .build()
    }



    @Provides
    fun provideCocheDao(appDatabase: AppDatabase): CocheDao {
        return appDatabase.cocheDao()
    }
}