package com.silverthread.tftstatistics.ui.unit

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
import com.silverthread.tftstatistics.databinding.FragmentStatBinding
import com.silverthread.tftstatistics.ui.common.DividerItemDecoration
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

class UnitStatFragment: Fragment() {

    private var _binding : FragmentStatBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()
    private val adapter = UnitStatsAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentStatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stats.layoutManager = LinearLayoutManager(requireActivity())
        binding.stats.adapter = adapter

        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        context?.let{
            binding.stats.addItemDecoration(
                    DividerItemDecoration(
                            ContextCompat.getColor(it, R.color.rarity_0),
                            heightInPixels
                    )
            )
        }

        summonerViewModel.summonerLiveData.observe(requireActivity(), Observer { summoner ->
            summoner.puuid?.let{
                refresh(it)
            }
        })

        summonerViewModel.unitStatLiveData.observe(requireActivity(), Observer { units ->
            adapter.updateUnitStat(units)
        })
    }

    private fun refresh(id: String){
        binding.swipeRefresh.setOnRefreshListener {
            summonerViewModel.loadSummonerByPuuid(id)
            binding.swipeRefresh.isRefreshing = false
        }
    }
}