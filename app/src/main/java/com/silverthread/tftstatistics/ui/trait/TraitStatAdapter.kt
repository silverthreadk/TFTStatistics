package com.silverthread.tftstatistics.ui.trait

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ListItemTraitStatBinding
import com.silverthread.tftstatistics.model.CompositeItem
import com.silverthread.tftstatistics.model.StatData
import com.silverthread.tftstatistics.util.getName
import com.silverthread.tftstatistics.util.getWinRate
import com.silverthread.tftstatistics.util.margin


class TraitStatsAdapter(private val compositeItems: MutableList<CompositeItem>): RecyclerView.Adapter<TraitStatsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private var binding = ListItemTraitStatBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: CompositeItem) {
            if (item.isHeader) {
                setupHeader()
            } else {
                setupItem(item.statData)
            }

        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }

        private fun setupHeader() {
            val context = itemView.context
            binding.apply{
                traitImage.visibility = GONE
                name.margin(left = 20f)
                name.text = context.resources.getString(R.string.trait)
                game.text = context.resources.getString(R.string.games_label)
                place.text = context.resources.getString(R.string.place_label)
                top4.text = context.resources.getString(R.string.top4_label)
                win.text = context.resources.getString(R.string.win_label)
            }
        }

        private fun setupItem(trait: StatData) {
            val context = itemView.context
            val traitName = trait.name.getName()
            val traitId = "trait_" + traitName.toLowerCase()
            val color = context.resources.getIdentifier("trait_" + trait.style, "color", context.packageName)
            val csl = context.getColorStateList(color)
            binding.apply{
                traitImage.setImageResource(
                        context.resources.getIdentifier(traitId, "drawable", context.packageName))
                traitImage.imageTintList = csl
                name.text = traitName
                game.text = trait.games.toString()
                place.text = String.format("%.2f", trait.place / trait.games.toFloat())
                top4.text = context.getWinRate(R.string.percent, trait.top4, 20)
                win.text = context.getWinRate(R.string.percent, trait.wins, 20)
            }
        }
    }

    enum class ViewType {
        HEADER, UNIT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_trait_stat, parent, false))
    }

    override fun getItemCount() = compositeItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(compositeItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (compositeItems[position].isHeader) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.UNIT.ordinal
        }
    }

    fun updateTraitStat(compositeItems: List<CompositeItem>) {
        this.compositeItems.clear()
        this.compositeItems.addAll(compositeItems)
        notifyDataSetChanged()
    }
}
