package com.silverthread.tftstatistics.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.FragmentSearchSummonerBinding
import com.silverthread.tftstatistics.model.constants.Region
import com.silverthread.tftstatistics.ui.search.SearchSummonerFragmentDirections
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

class SearchSummonerFragment : Fragment() {

    private var _binding: FragmentSearchSummonerBinding? = null
    private val binding get() = _binding!!
    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchSummonerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        binding.searchButton.setOnClickListener {
            val summonerName = binding.summonerInput.text.toString()
            if (summonerName.isNotBlank()) {
                summonerViewModel.loadSummoner(summonerName)
            }
        }

        ArrayAdapter.createFromResource(
                context!!,
                R.array.region_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                summonerViewModel.setRegion(Region.fromId(resources.getStringArray(R.array.region_array)[position]))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        summonerViewModel.searchEventLiveData.observe(requireActivity(), Observer {
            showSummonor()
        })
        summonerViewModel.regionLiveData.observe(requireActivity(), Observer { region ->
            binding.spinner.setSelection(region.ordinal, false)
        })
    }

    private fun showSummonor(){
        view?.let{
            val action = SearchSummonerFragmentDirections.actionSearchSummonerFragmentToSummonerTabFragment()
            it.findNavController().navigate(action)
        }
    }
}