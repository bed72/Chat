package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import android.content.Context

import androidx.datastore.dataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import com.bed.chat.SelfUser

import com.bed.chat.external.datasources.local.serializer.SelfUserSerializer

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideSelfUserDataStore(@ApplicationContext context: Context): DataStore<SelfUser> =
        dataStore(SelfUser::class.java.name, SelfUserSerializer)
            .getValue(context, SelfUserSerializer::javaClass)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = StorageModule.javaClass.name).getValue(context, String::javaClass)
}
