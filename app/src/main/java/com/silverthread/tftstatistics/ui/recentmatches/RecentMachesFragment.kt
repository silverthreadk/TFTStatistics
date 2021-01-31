package com.silverthread.tftstatistics.ui.recentmatches

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
import com.silverthread.tftstatistics.databinding.FragmentMatchHistoryBinding
import com.silverthread.tftstatistics.ui.common.DividerItemDecoration
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

class RecentMachesFragment  : Fragment() {

    private var _binding: FragmentMatchHistoryBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()

    private val adapter = RecentMachesAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMatchHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.matchHistoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.matchHistoryRecyclerView.adapter = adapter

        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        context?.let{
            binding.matchHistoryRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    ContextCompat.getColor(it, R.color.rarity_0),
                    heightInPixels
                )
            )
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            summonerViewModel.summonerLiveData.value?.puuid?.let{
                summonerViewModel.loadSummonerByPuuid(it)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        summonerViewModel.matchLiveData.observe(requireActivity(), Observer { match ->
            adapter.updateMatchHistory(match, summonerViewModel.summonerLiveData.value?.puuid?:"")
        })
    }
}