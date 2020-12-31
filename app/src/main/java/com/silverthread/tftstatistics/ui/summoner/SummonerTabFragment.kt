package com.silverthread.tftstatistics.ui.summoner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silverthread.tftstatistics.R
import kotlinx.android.synthetic.main.fragment_summoner_tab.*

class SummonerTabFragment : Fragment() {

    private val pagerAdapter by lazy {
        SummonerPagerAdapter(
            childFragmentManager
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summoner_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        tabs.setupWithViewPager(fragmentPager)
        fragmentPager.adapter = pagerAdapter
    }


}