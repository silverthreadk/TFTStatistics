package com.silverthread.tftstatistics.ui.summoner

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.FragmentSummonerTabBinding
import com.silverthread.tftstatistics.ui.main.MainActivity
import com.silverthread.tftstatistics.util.dismissKeyboard

class SummonerTabFragment : Fragment() {

    private var _binding: FragmentSummonerTabBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding = FragmentSummonerTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        summonerViewModel.regionLiveData.removeObservers(requireActivity())
        summonerViewModel.summonerLiveData.removeObservers(requireActivity())
        summonerViewModel.leagueEntryLiveData.removeObservers(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search(searchView, searchView.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchView.setOnClickListener { view ->
            search(view, searchView.query.toString())
        }
    }

    private fun setupUI() {
        binding.fragmentPager.adapter = SummonerPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.fragmentPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Recent Matches"
                1 -> tab.text = "Units"
                2 -> tab.text = "Traits"
            }
        }.attach()

        summonerViewModel.summonerLiveData.observe(requireActivity(), Observer { summoner ->
            binding.summoner.level.text = String.format(resources.getString(R.string.level), summoner.summonerLevel)
            binding.summoner.summonerName.text = summoner.name
            val url = "http://ddragon.leagueoflegends.com/cdn/10.25.1/img/profileicon/${summoner.profileIconId}.png"
            Glide.with(this)
                    .load(url)
                    .circleCrop()
                    .into(binding.summoner.summonerIcon)
        })
        summonerViewModel.leagueEntryLiveData.observe(requireActivity(), Observer { leagueEntry ->
            binding.summoner.tier.text = "${leagueEntry.tier} ${leagueEntry.rank}"
            val tierResource = resources.getIdentifier("emblem_" + leagueEntry.tier.toLowerCase(), "drawable", requireContext().packageName)
            binding.summoner.tierIcon.setImageResource(tierResource)
            binding.summoner.leaguePoints.text = String.format(resources.getString(R.string.lp), leagueEntry.leaguePoints)
            binding.summoner.games.text = String.format(resources.getString(R.string.games), (leagueEntry.wins + leagueEntry.losses))
            binding.summoner.wins.text = String.format(resources.getString(R.string.wins), leagueEntry.wins)
            val winRate = if (leagueEntry.wins + leagueEntry.losses > 0) {
                100f * leagueEntry.wins / (leagueEntry.wins + leagueEntry.losses)
            } else {
                0f
            }
            binding.summoner.winRate.text = String.format(resources.getString(R.string.win_rate), winRate)
        })
        summonerViewModel.regionLiveData.observe(requireActivity(), Observer { region ->
            binding.summoner.region.text = region.id
        })
    }


    private fun search(view: View, summonerName: String){
        if (summonerName.isNotBlank()) {
            summonerViewModel.loadSummoner(summonerName)
        }
        dismissKeyboard(view.windowToken)
    }
}