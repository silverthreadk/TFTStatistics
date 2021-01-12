package com.silverthread.tftstatistics.ui.matchHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel
import kotlinx.android.synthetic.main.fragment_match_history.*

class MatchHistoryFragment  : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()

    private val adapter = MatchHistoryAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchHistoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        matchHistoryRecyclerView.adapter = adapter

        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        context?.let{
            matchHistoryRecyclerView.addItemDecoration(DividerItemDecoration(ContextCompat.getColor(it, R.color.rarity_0), heightInPixels))
        }
    }

    override fun onResume(){
        super.onResume()
        summonerViewModel.MatchLiveData.observe(requireActivity(), Observer { match ->
            adapter.updateMatchHistory(match, summonerViewModel.summonerLiveData.value?.puuid?:"")
        })
    }
}