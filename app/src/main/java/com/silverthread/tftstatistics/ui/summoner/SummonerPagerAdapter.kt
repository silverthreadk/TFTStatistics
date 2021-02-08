package com.silverthread.tftstatistics.ui.summoner

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.silverthread.tftstatistics.ui.recentmatches.RecentMachesFragment
import com.silverthread.tftstatistics.ui.unit.UnitStatFragment

class SummonerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(RecentMachesFragment(), UnitStatFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) : Fragment {
        return fragments[position]
    }
}