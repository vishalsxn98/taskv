package com.vishal.videop.ui.home.domain.model

import android.net.Uri

data class VideoModel(
    val videoName: String,
    val duration: String,
    val uri: Uri,
    val thumb: String,
)