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
            binding.traitImage.visibility = GONE
            binding.name.margin(left = 20f)
            binding.name.text = context.resources.getString(R.string.trait)
            binding.game.text = context.resources.getString(R.string.games_label)
            binding.place.text = context.resources.getString(R.string.place_label)
            binding.top4.text = context.resources.getString(R.string.top4_label)
            binding.win.text = context.resources.getString(R.string.win_label)
        }

        private fun setupItem(trait: StatData) {
            val context = itemView.context
            lateinit var traitId: String
            lateinit var traitName: String
            if (trait.name.startsWith("Set4_")) {
                traitName = trait.name.substring(5)
                traitId = "trait_" + trait.name.substring(5).toLowerCase()
            } else {
                traitName = trait.name
                traitId = "trait_" + trait.name.toLowerCase()
            }

            binding.traitImage.setImageResource(
                    context.resources.getIdentifier(traitId, "drawable", context.packageName))
            val color = context.resources.getIdentifier("trait_" + trait.style, "color", context.packageName)
            val csl = context.getColorStateList(color)
            binding.traitImage.imageTintList = csl
            binding.name.text = traitName
            binding.game.text = trait.games.toString()
            binding.place.text = String.format("%.2f", trait.place / trait.games.toFloat())
            binding.top4.text = String.format(context.resources.getString(R.string.percent), 100 * trait.top4 / 20f)
            binding.win.text = String.format(context.resources.getString(R.string.percent), 100 * trait.wins / 20f)
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
