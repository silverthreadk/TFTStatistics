package com.silverthread.tftstatistics.ui.unit

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ListItemUnitStatBinding
import com.silverthread.tftstatistics.model.CompositeItem
import com.silverthread.tftstatistics.model.StatData
import com.silverthread.tftstatistics.util.getName
import com.silverthread.tftstatistics.util.getWinRate
import com.silverthread.tftstatistics.util.margin


class UnitStatsAdapter(private val compositeItems: MutableList<CompositeItem>): RecyclerView.Adapter<UnitStatsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private var binding = ListItemUnitStatBinding.bind(itemView)

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
                unitImage.visibility = GONE
                name.margin(left = 20f)
                name.text = context.resources.getString(R.string.unit)
                game.text = context.resources.getString(R.string.games_label)
                place.text = context.resources.getString(R.string.place_label)
                top4.text = context.resources.getString(R.string.top4_label)
                win.text = context.resources.getString(R.string.win_label)
            }
        }

        private fun setupItem(unit: StatData) {
            val context = itemView.context
            val charName = unit.name.getName()
            val charId = "tft4_" + charName.toLowerCase()

            binding.apply{
                unitImage.setImageResource(
                        context.resources.getIdentifier(charId.toLowerCase(), "drawable", context.packageName))
                unitImage.setBackgroundResource(
                        context.resources.getIdentifier("rarity_frame_"+unit.rarity, "drawable", context.packageName))
                name.text = charName
                game.text = unit.games.toString()
                place.text = String.format("%.2f", unit.place / unit.games.toFloat())
                top4.text = context.getWinRate(R.string.percent, unit.top4, 20)
                win.text = context.getWinRate(R.string.percent, unit.wins, 20)
            }
        }
    }

    enum class ViewType {
        HEADER, UNIT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_unit_stat, parent, false))
    }

    override fun getItemCount() = compositeItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(compositeItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if(compositeItems[position].isHeader){
            ViewType.HEADER.ordinal
        } else{
            ViewType.UNIT.ordinal
        }
    }

    fun updateUnitStat(compositeItems: List<CompositeItem>) {
        this.compositeItems.clear()
        this.compositeItems.addAll(compositeItems)
        notifyDataSetChanged()
    }
}
