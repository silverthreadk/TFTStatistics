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
import com.silverthread.tftstatistics.R
import kotlinx.android.synthetic.main.fragment_summoner.*


class SummonerFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summoner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        summonerViewModel.summonerLiveData.observe(requireActivity(), Observer { summoner ->
            summonerName.text = summoner.name
            val url = "http://ddragon.leagueoflegends.com/cdn/10.25.1/img/profileicon/${summoner.profileIconId}.png"
            Glide.with(this)
                .load(url)
                .into(imageSummonerIcon)
        })
        summonerViewModel.leagueEntryLiveData.observe(requireActivity(), Observer { leagueEntry ->
            tier.text = "${leagueEntry.tier} ${leagueEntry.rank}"
            val tierResource = resources.getIdentifier("emblem_" + leagueEntry.tier?.toLowerCase(), "drawable", requireContext().packageName)
            imageTierIcon.setImageResource(tierResource)
            leaguePoints.text = leagueEntry.leaguePoints + " LP"
            games.text = ((leagueEntry.wins?.toInt() ?: 0) + (leagueEntry.losses?.toInt() ?: 0)).toString()
            wins.text = leagueEntry.wins
            losses.text = leagueEntry.losses
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        summonerViewModel.summonerLiveData.removeObservers(requireActivity())
        summonerViewModel.leagueEntryLiveData.removeObservers(requireActivity())
    }
}