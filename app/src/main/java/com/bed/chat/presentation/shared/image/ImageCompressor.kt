package com.bed.chat.presentation.shared.image

import javax.inject.Inject

import java.io.File
import java.io.FileOutputStream

import android.net.Uri
import android.content.Context
import android.graphics.Matrix
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import androidx.exifinterface.media.ExifInterface

import dagger.hilt.android.qualifiers.ApplicationContext

interface ImageCompressor {
    suspend operator fun invoke(
        image: Uri,
        quality: Int = 72,
        maxWidth: Int = 1080,
        maxHeight: Int = 1080,
        type: Pair<String, Bitmap.CompressFormat> = ".jpg" to Bitmap.CompressFormat.JPEG
    ): File
}

class ImageCompressorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ImageCompressor {
    override suspend operator fun invoke(
        image: Uri,
        quality: Int,
        maxWidth: Int,
        maxHeight: Int,
        type: Pair<String, Bitmap.CompressFormat>
    ): File = withContext(Dispatchers.IO)  {
        // Carrega o Bitmap a partir do Uri
        val original = uriToBitmap(image)
            ?.let { bitmap ->
                // Ajusta a orientação do bitmap antes de redimensionar
                adjustBitmapOrientation(image, bitmap)
            } ?: throw IllegalArgumentException("Imagem não encontrada")

        // Redimensiona a imagem, se necessário
        val resized = if (original.width > maxWidth || original.height > maxHeight) {
            resizeBitmap(original, maxWidth, maxHeight)
        } else original


        // Salva o bitmap comprimido em um arquivo temporário
        val compressed = File(context.cacheDir, "${System.currentTimeMillis()}${type.first}")
        FileOutputStream(compressed).use { resized.compress(type.second, quality, it) }

        compressed
    }

    /**
     * Converte um Uri em um Bitmap.
     * Esta função é suspensa para operações com coroutines.
     *
     * @param uri Uri da imagem.
     * @return Bitmap ou null se a imagem não for encontrada.
     */
    private fun uriToBitmap(uri: Uri): Bitmap? = try {
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        } catch (exception: Exception) {
            exception.printStackTrace()

            null
        }

    /**
     * Redimensiona o Bitmap mantendo a proporção.
     *
     * @param bitmap Bitmap a ser redimensionado.
     * @param maxWidth Largura máxima.
     * @param maxHeight Altura máxima.
     * @return Bitmap redimensionado.
     */
    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
        val width = if (aspectRatio > 1) maxWidth else (maxHeight * aspectRatio).toInt()
        val height = if (aspectRatio > 1) (maxWidth / aspectRatio).toInt() else maxHeight

        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    /**
     * Ajusta a orientação do Bitmap com base nos metadados EXIF.
     *
     * @param image Uri da imagem.
     * @param bitmap Bitmap original.
     * @return Bitmap com a orientação corrigida.
     */
    private fun adjustBitmapOrientation(image: Uri, bitmap: Bitmap): Bitmap {
        val inputStream = context.contentResolver.openInputStream(image)
        val exif = inputStream?.use { ExifInterface(it) }
        val orientation = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        ) ?: ExifInterface.ORIENTATION_NORMAL

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}