package com.milovanjakovljevic.targetvideo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.milovanjakovljevic.targetvideo.databinding.ItemVideoBinding
import com.milovanjakovljevic.targetvideo.entities.DataEntity

class VideoAdapter : ListAdapter<DataEntity, VideoAdapter.VideoViewHolder>(VideoDiffUtil()) {

    interface IVideoClickListener {
        fun onVideoClick(videoUrl: String?, videoDescription: String?, videoTitle: String?)
    }

    lateinit var videoClickListener: IVideoClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video: DataEntity = getItem(position)
        holder.bind(video)
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: DataEntity) {
            Glide.with(binding.videoThumbnail)
                .load(video.thumbnailImage)
                .into(binding.videoThumbnail)

            binding.videoThumbnail.setOnClickListener {
                videoClickListener.onVideoClick(video.videoMp4, video.description, video.videoTitle)
            }
            // JSON doesn't provide in his response title of video , so I put file name to be title
            binding.videoTitle.text = video.videoTitle
        }
    }

    class VideoDiffUtil : DiffUtil.ItemCallback<DataEntity>() {
        override fun areItemsTheSame(
            oldItem: DataEntity,
            newItem: DataEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataEntity,
            newItem: DataEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
