package fr.skyle.scanny.utils

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import fr.skyle.scanny.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object ShareUtils {

    @TargetApi(Build.VERSION_CODES.BASE)
    fun getOutputStream(context: Context, fileName: String): OutputStream =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getOutPutStreamForApiAboveQ(context, fileName) ?: getOutputStreamForApiUnderQ(fileName)
        } else getOutputStreamForApiUnderQ(fileName)

    private fun getOutPutStreamForApiAboveQ(context: Context, fileName: String): OutputStream? {
        val relativePath =
            Environment.DIRECTORY_PICTURES +
                File.separator + context.getString(R.string.app_name)

        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        }

        val baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        return resolver.insert(baseUri, contentValues)?.let {
            resolver.openOutputStream(it)
        }
    }

    private fun getOutputStreamForApiUnderQ(fileName: String): FileOutputStream {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        // Create folder
        directory.mkdirs()

        // Get File
        val file = File(directory, fileName)

        // Return os
        return FileOutputStream(file)
    }
}