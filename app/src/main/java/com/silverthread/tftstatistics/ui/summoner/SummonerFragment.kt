package com.silverthread.tftstatistics.ui.summoner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.FragmentSummonerBinding


class SummonerFragment : Fragment() {

    private var _binding: FragmentSummonerBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSummonerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        summonerViewModel.summonerLiveData.observe(requireActivity(), Observer { summoner ->
            binding.level.text = String.format(resources.getString(R.string.level), summoner.summonerLevel)
            binding.summonerName.text = summoner.name
            val url = "http://ddragon.leagueoflegends.com/cdn/10.25.1/img/profileicon/${summoner.profileIconId}.png"
            Glide.with(this)
                .load(url)
                .circleCrop()
                .into(binding.summonerIcon)
        })
        summonerViewModel.leagueEntryLiveData.observe(requireActivity(), Observer { leagueEntry ->
            binding.tier.text = "${leagueEntry.tier} ${leagueEntry.rank}"
            val tierResource = resources.getIdentifier("emblem_" + leagueEntry.tier?.toLowerCase(), "drawable", requireContext().packageName)
            binding.tierIcon.setImageResource(tierResource)
            binding.leaguePoints.text = String.format(resources.getString(R.string.lp), leagueEntry.leaguePoints)
            binding.games.text = String.format(resources.getString(R.string.games), (leagueEntry.wins + leagueEntry.losses))
            binding.wins.text = String.format(resources.getString(R.string.wins), leagueEntry.wins)
            binding.winRate.text = String.format(resources.getString(R.string.win_rate), (100 * leagueEntry.wins / (leagueEntry.wins + leagueEntry.losses)))
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