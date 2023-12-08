package com.milovanjakovljevic.targetvideo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.milovanjakovljevic.targetvideo.databinding.ItemVideoBinding
import com.milovanjakovljevic.targetvideo.entities.ItemsEntity

class VideoAdapter : ListAdapter<ItemsEntity, VideoAdapter.VideoViewHolder>(VideoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video: ItemsEntity = getItem(position)
        holder.bind(video)
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: ItemsEntity) {
            Glide.with(binding.videoThumbnail)
                .load(video.snippet?.thumbnail?.default?.url)
                .into(binding.videoThumbnail)
            binding.videoTitle.text = video.snippet?.title
        }
    }

    class VideoDiffUtil : DiffUtil.ItemCallback<ItemsEntity>() {
        //Fixme oldItem.id == newItem.id
        override fun areItemsTheSame(
            oldItem: ItemsEntity,
            newItem: ItemsEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ItemsEntity,
            newItem: ItemsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
