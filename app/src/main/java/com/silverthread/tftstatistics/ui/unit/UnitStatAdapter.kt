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
import com.silverthread.tftstatistics.util.margin


class UnitStatsAdapter(private val compositeItems: MutableList<CompositeItem>): RecyclerView.Adapter<UnitStatsAdapter.ViewHolder>() {
    private var puuid = ""

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private var binding = ListItemUnitStatBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: CompositeItem) {
            if(item.isHeader){
                setupHeader()
            } else {
                setupItem(item.statData)
            }

        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }

        private fun setupHeader(){
            val context = itemView.context
            binding.unitImage.visibility = GONE
            binding.name.margin(left = 20f)
            binding.name.text = context.resources.getString(R.string.unit)
            binding.game.text = context.resources.getString(R.string.games_label)
            binding.place.text = context.resources.getString(R.string.place_label)
            binding.top4.text = context.resources.getString(R.string.top4_label)
            binding.win.text = context.resources.getString(R.string.win_label)
        }

        private fun setupItem(unit: StatData){
            val context = itemView.context
            lateinit var charId: String
            lateinit var charName: String
            unit.name?.let{
                if (it.startsWith("TFT4b_")) {
                    charName = it.substring(6)
                    charId = "tft4_" + charName.toLowerCase()
                } else {
                    charName = it.substring(5)
                    charId = it.toLowerCase()
                }
            }
            binding.unitImage.setImageResource(
                    context.resources.getIdentifier(charId.toLowerCase(), "drawable", context.packageName))
            binding.unitImage.setBackgroundResource(
                    context.resources.getIdentifier("rarity_frame_"+unit.rarity, "drawable", context.packageName))
            binding.name.text = charName
            binding.game.text = unit.games.toString()
            binding.place.text = String.format("%.2f", unit.place / unit.games.toFloat())
            binding.top4.text = String.format(context.resources.getString(R.string.percent), 100 * unit.top4 / 20f)
            binding.win.text = String.format(context.resources.getString(R.string.percent), 100 * unit.wins / 20f)
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
        return super.getItemViewType(position)
    }

    fun updateUnitStat(compositeItems: List<CompositeItem>) {
        this.compositeItems.clear()
        this.compositeItems.addAll(compositeItems)
        notifyDataSetChanged()
    }
}
