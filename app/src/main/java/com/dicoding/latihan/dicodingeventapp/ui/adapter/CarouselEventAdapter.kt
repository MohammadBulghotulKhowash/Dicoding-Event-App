package com.dicoding.latihan.dicodingeventapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.latihan.dicodingeventapp.databinding.CarouselCardItemBinding

class CarouselEventAdapter(
    private val onClickEvent: (Int) -> Unit
): ListAdapter<ListEventsItem, CarouselEventAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: CarouselCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onClickEvent: (Int) -> Unit) {
            binding.root.setOnClickListener {
                event.id?.let { itId -> onClickEvent(itId) }
            }

            val ivImageLogo = binding.ivImageLogo
            Glide.with(ivImageLogo).load(event.imageLogo).into(ivImageLogo)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
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
        val binding = CarouselCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getItem = getItem(position)
        if (getItem != null) {
            holder.bind(getItem, onClickEvent)
        }
    }
}