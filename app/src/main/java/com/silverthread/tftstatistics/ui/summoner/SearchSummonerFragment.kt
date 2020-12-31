package com.silverthread.tftstatistics.ui.summoner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.silverthread.tftstatistics.R
import kotlinx.android.synthetic.main.fragment_search_summoner.*

class SearchSummonerFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_summoner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        searchButton.setOnClickListener {
            val summonerName = summonerInput.text.toString()
            if (summonerName.isNotBlank()) {
                summonerViewModel.getSummoner(summonerName)
                showSummonor()
            } else {

            }
        }
    }

    private fun showSummonor(){
        view?.let{
            val action = SearchSummonerFragmentDirections.actionSearchSummonerFragmentToSummonerTabFragment()
            it.findNavController().navigate(action)
        }
    }
}