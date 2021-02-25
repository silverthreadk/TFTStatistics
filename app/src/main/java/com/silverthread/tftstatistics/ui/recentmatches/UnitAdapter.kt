package com.silverthread.tftstatistics.ui.recentmatches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ListItemUnitBinding
import com.silverthread.tftstatistics.model.response.UnitDTO

class UnitAdapter(private val units: MutableList<UnitDTO>): RecyclerView.Adapter<UnitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var unit: UnitDTO
        private val binding = ListItemUnitBinding.bind(itemView)
        private val itemAdapter = ItemAdapter(mutableListOf())
        private val tierAdapter = TierAdapter(mutableListOf())

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(unit: UnitDTO) {
            this.unit = unit
            val context = itemView.context
            val charId = unit.character_id.let{
                if (it.startsWith("TFT4b_")) {
                    "tft4_" + it.substring(6).toLowerCase()
                } else {
                    it.toLowerCase()
                }
            }
            binding.unitImage.setImageResource(
                    context.resources.getIdentifier(charId, "drawable", context.packageName))
            binding.unitImage.setBackgroundResource(
                    context.resources.getIdentifier("rarity_frame_"+unit.rarity, "drawable", context.packageName))
            setupItems(unit.items)
            setupTier(unit.tier.toInt(), unit.rarity)
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }

        private fun setupItems(items: List<String>) {
            binding.ItemRecyclerView.layoutManager =
                    GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false)
            binding.ItemRecyclerView.adapter = itemAdapter
            itemAdapter.updateItems(items)
        }

        private fun setupTier(tier: Int, rarity: String) {
            binding.TierRecyclerView.layoutManager =
                GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false)
            binding.TierRecyclerView.adapter = tierAdapter

            val tiers = arrayListOf<String>()
            for (i in 0 until tier) {
                tiers.add(rarity)
            }
            tierAdapter.updateTiers(tiers)
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
        this.units.sortedBy {
            it.rarity
        }
        notifyDataSetChanged()
    }
}
