package com.example.instagram.feature.post.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class LocalPostDataSource(private val context: Context): PostDataSource {

    override suspend fun fetchPictures(): List<Uri> {
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else{
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH
        )

        val photos = mutableListOf<Uri>()

        context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            "${MediaStore.Images.Media._ID} DESC"
        )?.use { cursor ->  
            val columnId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()){
                val id = cursor.getLong(columnId)
                val currentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                photos.add(currentUri)
                if(photos.size == 99) break
            }
        }

        return photos
    }

}