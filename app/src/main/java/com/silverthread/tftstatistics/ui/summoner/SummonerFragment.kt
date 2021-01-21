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
            binding.summonerName.text = summoner.name
            val url = "http://ddragon.leagueoflegends.com/cdn/10.25.1/img/profileicon/${summoner.profileIconId}.png"
            Glide.with(this)
                .load(url)
                .into(binding.imageSummonerIcon)
        })
        summonerViewModel.leagueEntryLiveData.observe(requireActivity(), Observer { leagueEntry ->
            binding.tier.text = "${leagueEntry.tier} ${leagueEntry.rank}"
            val tierResource = resources.getIdentifier("emblem_" + leagueEntry.tier?.toLowerCase(), "drawable", requireContext().packageName)
            binding.imageTierIcon.setImageResource(tierResource)
            binding.leaguePoints.text = leagueEntry.leaguePoints + " LP"
            binding.games.text = ((leagueEntry.wins?.toInt() ?: 0) + (leagueEntry.losses?.toInt() ?: 0)).toString()
            binding.wins.text = leagueEntry.wins
            binding.losses.text = leagueEntry.losses
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
        summonerViewModel.summonerLiveData.removeObservers(requireActivity())
        summonerViewModel.leagueEntryLiveData.removeObservers(requireActivity())
    }
}