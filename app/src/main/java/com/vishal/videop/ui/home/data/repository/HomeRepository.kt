package com.vishal.videop.ui.home.data.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.vishal.videop.ui.home.domain.model.VideoModel
import javax.inject.Inject

class HomeRepository @Inject constructor(private val context: Context) {

    fun getVideoList(): MutableList<VideoModel> {
        val list = mutableListOf<VideoModel>()
        val contentResolver: ContentResolver = context.contentResolver
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)


        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                val duration: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                val data: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                val thumb: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA))
                val videoModel = VideoModel(title, duration, Uri.parse(data), thumb)
                list.add(videoModel)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return list

    }
}