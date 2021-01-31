package com.silverthread.tftstatistics.ui.summoner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.silverthread.tftstatistics.ui.recentmatches.RecentMachesFragment
import com.silverthread.tftstatistics.ui.unit.UnitFragment

class SummonerPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = listOf(RecentMachesFragment(), UnitFragment())
    private val titles = listOf("Recent matches", "Units")

    override fun getCount(): Int = fragments.size
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}