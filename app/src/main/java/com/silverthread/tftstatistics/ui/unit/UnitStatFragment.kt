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
import com.silverthread.tftstatistics.databinding.FragmentUnitBinding
import com.silverthread.tftstatistics.ui.common.DividerItemDecoration
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

class UnitStatFragment: Fragment() {

    private var _binding : FragmentUnitBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()
    private val adapter = UnitStatsAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentUnitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.units.layoutManager = LinearLayoutManager(requireActivity())
        binding.units.adapter = adapter

        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        context?.let{
            binding.units.addItemDecoration(
                    DividerItemDecoration(
                            ContextCompat.getColor(it, R.color.rarity_0),
                            heightInPixels
                    )
            )
        }

        binding.swipeRefresh.setOnRefreshListener {
            summonerViewModel.summonerLiveData.value?.puuid?.let{
                summonerViewModel.loadSummonerByPuuid(it)
            }
            binding.swipeRefresh.isRefreshing = false
        }

        summonerViewModel.unitStatLiveData.observe(requireActivity(), Observer { units ->
            adapter.updateUnitStat(units)
        })
    }
}