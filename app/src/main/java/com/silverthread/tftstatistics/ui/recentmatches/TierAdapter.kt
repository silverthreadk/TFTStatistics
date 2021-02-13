package com.silverthread.tftstatistics.ui.recentmatches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ListItemTierBinding

class TierAdapter(private val tiers: MutableList<String>): RecyclerView.Adapter<TierAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var tier: String
        private val binding = ListItemTierBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: String) {
            this.tier = item
            val context = itemView.context
            val color = context.getColor(context.resources.getIdentifier("rarity_" + item, "color", context.packageName))
            binding.tierIcon.color = color
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_tier, parent, false))
    }

    override fun getItemCount() = tiers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tiers[position])
    }

    fun updateTiers(items: List<String>) {
        this.tiers.clear()
        this.tiers.addAll(items)
        notifyDataSetChanged()
    }
}
