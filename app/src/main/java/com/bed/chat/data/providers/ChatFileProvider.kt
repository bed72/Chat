package com.bed.chat.data.providers

import java.io.File

import android.net.Uri
import android.content.Context

import androidx.core.content.FileProvider

import com.bed.chat.R

class ChatFileProvider : FileProvider(R.xml.file_paths) {
    companion object  {
        operator fun invoke(context: Context): Uri =
            getUriForFile(
                context,
                 "${context.packageName}.fileprovider",
                File.createTempFile("profile_picture", ".jpg", context.cacheDir)
            )
    }
}
