package com.silverthread.tftstatistics.ui.matchHistory

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

class MatchHistoryAdapter(private val matches: MutableList<MatchDTO>): RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder>() {
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

            var dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(match.info?.game_datetime?.toLong())
            binding.gameDatetime.text = dateFormat

            var gameLength= (match.info?.game_length?.toDouble()?.div(60)).toString()
            gameLength = gameLength.substring(0,2)+":"+ gameLength.substring(3,5);
            binding.gameLength.text = gameLength
            binding.type.text = if (match.info?.queue_id == "1100") "Ranked" else "Normal"

            match.info?.participants?.filter { participant ->
                participant.puuid == puuid
            }?.forEach{ participant ->
                binding.placement.text = "#" + participant.placement
                setupUnits(participant.units)
                setupTraits(participant.traits)
            }
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }

        private fun setupUnits(units: List<UnitDTO>?) {
            if (units.isNullOrEmpty()) return
            binding.UnitRecyclerView.layoutManager =
                    GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
            binding.UnitRecyclerView.adapter = unitAdapter
            unitAdapter.updateUnits(units)
        }

        private fun setupTraits(traits: List<TraitDTO>?) {
            if (traits.isNullOrEmpty()) return
            binding.TraitRecyclerView.layoutManager =
                    GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
            binding.TraitRecyclerView.adapter = traitAdapter
            traitAdapter.updateTraits(traits.filter{it.style != "0"}.map{it})
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

    fun updateMatchHistory(matches: List<MatchDTO>, puuid: String) {
        this.matches.clear()
        this.matches.addAll(matches)
        this.puuid = puuid
        notifyDataSetChanged()
    }
}
