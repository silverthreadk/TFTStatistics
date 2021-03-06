package com.silverthread.tftstatistics.ui.summoner

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.silverthread.tftstatistics.ui.recentmatches.RecentMatchesFragment
import com.silverthread.tftstatistics.ui.trait.TraitStatFragment
import com.silverthread.tftstatistics.ui.unit.UnitStatFragment

class SummonerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(RecentMatchesFragment(), UnitStatFragment(), TraitStatFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) : Fragment {
        return fragments[position]
    }
}