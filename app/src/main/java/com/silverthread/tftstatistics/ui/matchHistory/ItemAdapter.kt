package com.silverthread.tftstatistics.ui.matchHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ListItemItemBinding

class ItemAdapter(private val items: MutableList<String>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var item: String
        private val binding = ListItemItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: String) {
            this.item = item
            val context = itemView.context
            binding.itemImage.setImageResource(
                    context.resources.getIdentifier("item_$item", "drawable", context.packageName))
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
