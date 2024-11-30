package com.bed.chat.external.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.chat.presentation.shared.image.ImageCompressor
import com.bed.chat.presentation.shared.image.ImageCompressorImpl

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationModule {
    @Binds
    fun bindImageCompressor(compressor: ImageCompressorImpl): ImageCompressor
}
