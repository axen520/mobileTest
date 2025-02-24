package com.example.mobiletest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mobiletest.databinding.ItemBookingBinding
import com.example.mobiletest.entity.BookingEntity

class BookingListAdapter : ListAdapter<BookingEntity, BookingListAdapter.BookingViewHolder>(
    COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<BookingEntity>() {
            override fun areItemsTheSame(oldItem: BookingEntity, newItem: BookingEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: BookingEntity,
                newItem: BookingEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class BookingViewHolder(val binding: ItemBookingBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        return BookingViewHolder(
            ItemBookingBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val binding = holder.binding
        val data = getItem(position)
        binding.id.text = "id: ${data.id?.toString() ?: "0"}"
        val oriAndDest = data.originAndDestinationPair
        binding.oriCode.text = "oriCode: ${oriAndDest?.origin?.code ?: ""}"
        binding.oriName.text = "oriName: ${oriAndDest?.origin?.displayName ?: ""}"
        binding.oriUrl.text = "oriUrl: ${oriAndDest?.origin?.url ?: ""}"
        binding.oriCity.text = "oriCity: ${oriAndDest?.originCity ?: ""}"
        binding.destCode.text = "destCode: ${oriAndDest?.destination?.code ?: ""}"
        binding.destName.text = "destName: ${oriAndDest?.destination?.displayName ?: ""}"
        binding.destUrl.text = "destUrl: ${oriAndDest?.destination?.url ?: ""}"
        binding.destCity.text = "destCity: ${oriAndDest?.destinationCity ?: ""}"
    }
}