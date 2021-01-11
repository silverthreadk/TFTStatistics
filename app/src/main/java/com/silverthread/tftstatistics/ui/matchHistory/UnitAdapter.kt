package com.silverthread.tftstatistics.ui.matchHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.model.response.UnitDTO
import kotlinx.android.synthetic.main.list_item_match.view.*
import kotlinx.android.synthetic.main.list_item_unit.view.*

class UnitAdapter(private val units: MutableList<UnitDTO>): RecyclerView.Adapter<UnitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var unit: UnitDTO
        private val adapter = ItemAdapter(mutableListOf())

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(unit: UnitDTO) {
            this.unit = unit
            val context = itemView.context
            itemView.unitImage.setImageResource(
                    context.resources.getIdentifier(unit.character_id?.toLowerCase(), "drawable", context.packageName))
            itemView.unitImage.setBackgroundResource(
                    context.resources.getIdentifier("rarity_frame_"+unit.rarity, "drawable", context.packageName))
            setupItems(unit.items)
            setupTier(unit.tier?.toInt(), unit.rarity)
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }

        private fun setupItems(items: List<String>?) {
            if (items.isNullOrEmpty()) return
            itemView.ItemRecyclerView.layoutManager =
                    GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false)
            itemView.ItemRecyclerView.adapter = adapter
            adapter.updateItems(items)
        }

        private fun setupTier(tier: Int?, rarity: String?) {
            if (tier == null || rarity == null) return

            when(tier) {
                1-> {
                    itemView.tierIcon2.visibility = View.INVISIBLE
                    itemView.tierIcon3.visibility = View.INVISIBLE
                }

                2-> {
                    itemView.tierIcon3.visibility = View.INVISIBLE
                }
            }

            val context = itemView.context
            val color = context.resources.getColor(context.resources.getIdentifier("rarity_" + rarity, "color", context.packageName))
            itemView.tierIcon1.color = color
            itemView.tierIcon2.color = color
            itemView.tierIcon3.color = color
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_unit, parent, false))
    }

    override fun getItemCount() = units.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(units[position])
    }

    fun updateUnits(units: List<UnitDTO>) {
        this.units.clear()
        this.units.addAll(units)
        notifyDataSetChanged()
    }
}
