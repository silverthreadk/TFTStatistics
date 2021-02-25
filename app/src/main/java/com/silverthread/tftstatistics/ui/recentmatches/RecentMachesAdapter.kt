package com.silverthread.tftstatistics.ui.recentmatches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ListItemMatchBinding
import com.silverthread.tftstatistics.model.response.MatchDTO
import com.silverthread.tftstatistics.model.response.TraitDTO
import com.silverthread.tftstatistics.model.response.UnitDTO
import java.text.SimpleDateFormat
import java.util.*

class RecentMachesAdapter(private val matches: MutableList<MatchDTO>): RecyclerView.Adapter<RecentMachesAdapter.ViewHolder>() {
    private var puuid = ""

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        private lateinit var match: MatchDTO
        private val binding = ListItemMatchBinding.bind(itemView)
        private val unitAdapter = UnitAdapter(mutableListOf())
        private val traitAdapter = TraitAdapter(mutableListOf())

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(match: MatchDTO) {
            this.match = match
            val context = itemView.context
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(match.info.game_datetime.toLong())
            binding.gameDatetime.text = dateFormat

            var gameLength = (match.info.game_length.toDouble().div(60)).toString()
            binding.gameLength.text = gameLength.substring(0,2)+":"+ gameLength.substring(3,5)
            binding.type.text = if (match.info.queue_id == "1100") itemView.context.getString(R.string.ranked) else itemView.context.getString(R.string.normal)

            match.info.participants.find { participant ->
                participant.puuid == puuid }?.let { participant ->
                binding.placement.text = String.format(itemView.context.getString(R.string.placement), participant.placement)
                var color = context.getColor(context.resources.getIdentifier("rarity_" + maxOf(0,5 - participant.placement), "color", context.packageName))
                binding.border.setBackgroundColor(color)
                if (participant.placement > 4) color = context.getColor(R.color.material_on_surface_emphasis_medium)
                binding.placement.setTextColor(color)

                setupUnits(participant.units)
                setupTraits(participant.traits)
            }
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }

        private fun setupUnits(units: List<UnitDTO>) {
            binding.UnitRecyclerView.layoutManager =
                    GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
            binding.UnitRecyclerView.adapter = unitAdapter
            unitAdapter.updateUnits(units)
        }

        private fun setupTraits(traits: List<TraitDTO>) {
            binding.TraitRecyclerView.layoutManager =
                    GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
            binding.TraitRecyclerView.adapter = traitAdapter
            traitAdapter.updateTraits(traits.filter{it.style != 0}.map{it})
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_match, parent, false))
    }

    override fun getItemCount() = matches.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    fun updateMatchHistory(matches: List<MatchDTO>) {
        this.matches.clear()
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }

    fun updatePuuid(puuid: String){
        this.puuid = puuid
        notifyDataSetChanged()
    }
}
