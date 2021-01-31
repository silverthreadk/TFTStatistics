package com.silverthread.tftstatistics.ui.unit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.silverthread.tftstatistics.databinding.FragmentUnitBinding
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

class UnitFragment: Fragment() {

    private var _binding : FragmentUnitBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentUnitBinding.inflate(inflater, container, false)
        return binding.root
    }
}