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
import com.silverthread.tftstatistics.util.refresh
import com.silverthread.tftstatistics.util.setupItemDecoration

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
        context?.let { binding.matchHistoryRecyclerView.setupItemDecoration(it) }

        binding.swipeRefresh.refresh(summonerViewModel)

        summonerViewModel.summonerLiveData.observe(getViewLifecycleOwner(), Observer { summoner ->
            adapter.updatePuuid(summoner.puuid)
        })

        summonerViewModel.matchLiveData.observe(getViewLifecycleOwner(), Observer { match ->
            adapter.updateMatchHistory(match)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}