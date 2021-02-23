package com.harsh.autoscoll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.harsh.autoscoll.ScrollAdapter.SliderViewHolder
import com.harsh.autoscoll.databinding.ItemScrollBinding

class ScrollAdapter(
    private val scrollItemList: MutableList<ScrollItem>,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<SliderViewHolder>() {

    var runnable = Runnable {
        scrollItemList.addAll(scrollItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            ItemScrollBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.binding.ivSlide.setImageResource(scrollItemList[position].image)
        if (position == scrollItemList.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return scrollItemList.size
    }

    inner class SliderViewHolder(val binding: ItemScrollBinding) :
        RecyclerView.ViewHolder(binding.root)
}