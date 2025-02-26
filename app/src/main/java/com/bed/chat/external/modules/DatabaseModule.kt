package com.bed.chat.external.modules

import androidx.room.Room

import javax.inject.Singleton

import android.content.Context

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import com.bed.chat.external.clients.database.ChatDatabase
import com.bed.chat.external.clients.database.DatabaseInformation

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ChatDatabase = Room.databaseBuilder(
        context = context,
        ChatDatabase::class.java,
        DatabaseInformation.NAME
    ).build()
}
