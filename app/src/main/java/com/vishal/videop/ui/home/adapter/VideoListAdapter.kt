package com.vishal.videop.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishal.videop.R
import com.vishal.videop.databinding.ListItemVideoBinding
import com.vishal.videop.ui.home.domain.model.VideoModel
import java.util.concurrent.TimeUnit

class VideoListAdapter(private val itemClickHandler: (path: String) -> Unit) :
    ListAdapter<VideoModel, VideoViewHolder>(VideoItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = DataBindingUtil.inflate<ListItemVideoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_video,
            null,
            false
        )
        return VideoViewHolder(binding, itemClickHandler)
    }


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class VideoViewHolder(
    private val binding: ListItemVideoBinding,
    private val itemClickHandler: (path: String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(model: VideoModel) {

        Glide.with(binding.root.context)
            .load(model.uri.path)
            .into(binding.thumb)
        binding.root.setOnClickListener {
            model.uri.path?.let { it1 -> itemClickHandler(it1) }
        }
        val time = model.duration.toLong()
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(time)
                % 60)
        binding.duration.text="$minutes:$seconds"
    }
}

class VideoItemDiffCallback : DiffUtil.ItemCallback<VideoModel>() {
    override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean =
        oldItem == newItem

}