package com.silverthread.tftstatistics.ui.summoner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.silverthread.tftstatistics.databinding.FragmentSummonerBinding
import kotlin.math.roundToInt


class SummonerFragment : Fragment() {

    private var _binding: FragmentSummonerBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSummonerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        summonerViewModel.summonerLiveData.observe(requireActivity(), Observer { summoner ->
            binding.level.text = "LV. ${summoner.summonerLevel}"
            binding.summonerName.text = summoner.name
            val url = "http://ddragon.leagueoflegends.com/cdn/10.25.1/img/profileicon/${summoner.profileIconId}.png"
            Glide.with(this)
                .load(url)
                .circleCrop()
                .into(binding.imageSummonerIcon)
        })
        summonerViewModel.leagueEntryLiveData.observe(requireActivity(), Observer { leagueEntry ->
            binding.tier.text = "${leagueEntry.tier} ${leagueEntry.rank}"
            val tierResource = resources.getIdentifier("emblem_" + leagueEntry.tier?.toLowerCase(), "drawable", requireContext().packageName)
            binding.imageTierIcon.setImageResource(tierResource)
            binding.leaguePoints.text = leagueEntry.leaguePoints + " LP"
            binding.games.text = (leagueEntry.wins + leagueEntry.losses).toString()
            binding.wins.text = leagueEntry.wins.toString()
            binding.winRate.text = String.format("%.2f %%", (leagueEntry.wins.toDouble() / (leagueEntry.wins + leagueEntry.losses)))
        })
        summonerViewModel.regionLiveData.observe(requireActivity(), Observer { region ->
            binding.region.text = region.id
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            summonerViewModel.summonerLiveData.value?.puuid?.let{
                summonerViewModel.loadSummonerByPuuid(it)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
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
}