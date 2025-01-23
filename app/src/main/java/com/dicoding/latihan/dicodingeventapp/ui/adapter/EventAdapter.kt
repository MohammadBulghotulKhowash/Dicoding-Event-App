package com.dicoding.latihan.dicodingeventapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.latihan.dicodingeventapp.databinding.EventItemBinding

class EventAdapter (
    private val onClickEvent: (Int) -> Unit
): ListAdapter<ListEventsItem, EventAdapter.ViewHolder>(DIFF_CALL) {

    inner class ViewHolder(private val binding: EventItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onClickEvent: (Int) -> Unit) {
            binding.root.setOnClickListener {
                event.id?.let { itId -> onClickEvent(itId) }
            }

            val ivMediaCover = binding.imageView
            Glide.with(ivMediaCover).load(event.mediaCover).into(ivMediaCover)
            binding.textView.text = event.name
        }
    }

    companion object {
        val DIFF_CALL = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getItem = getItem(position)
        if (getItem != null) {
            holder.bind(getItem, onClickEvent)
        }
    }
}

