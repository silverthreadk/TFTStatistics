package com.silverthread.tftstatistics.ui.summoner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silverthread.tftstatistics.databinding.FragmentSummonerTabBinding

class SummonerTabFragment : Fragment() {

    private var _binding: FragmentSummonerTabBinding? = null
    private val binding get() = _binding!!
    private val pagerAdapter by lazy {
        SummonerPagerAdapter(
            childFragmentManager
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSummonerTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        binding.tabs.setupWithViewPager(binding.fragmentPager)
        binding.fragmentPager.adapter = pagerAdapter
    }


}