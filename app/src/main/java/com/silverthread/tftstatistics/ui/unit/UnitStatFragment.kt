package com.silverthread.tftstatistics.ui.unit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.silverthread.tftstatistics.databinding.FragmentStatBinding
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel
import com.silverthread.tftstatistics.util.refresh
import com.silverthread.tftstatistics.util.setupItemDecoration

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
        context?.let { binding.stats.setupItemDecoration(it) }

        binding.swipeRefresh.refresh(summonerViewModel)

        summonerViewModel.unitStatLiveData.observe(viewLifecycleOwner, Observer { units ->
            adapter.updateUnitStat(units)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}