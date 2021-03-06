package com.silverthread.tftstatistics.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel
import com.silverthread.tftstatistics.util.EventObserver
import com.silverthread.tftstatistics.util.dismissKeyboard

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
        binding.search.setOnClickListener {
            search(it)
        }

        setupSearchInputListener()

        ArrayAdapter.createFromResource(
                requireContext(),
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

        summonerViewModel.searchEventLiveData.observe(viewLifecycleOwner, EventObserver {
            showSummoner()
        })
        summonerViewModel.regionLiveData.observe(viewLifecycleOwner, Observer { region ->
            binding.spinner.setSelection(region.ordinal, false)
        })
    }

    private fun setupSearchInputListener(){
        binding.summonerInput.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                search(view)
                true
            } else {
                false
            }
        }
        binding.summonerInput.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                search(view)
                true
            } else {
                false
            }
        }
    }

    private fun search(view: View){
        val summonerName = binding.summonerInput.text.toString()
        if (summonerName.isNotBlank()) {
            summonerViewModel.loadSummoner(summonerName)
        }
        dismissKeyboard(view.windowToken)
    }

    private fun showSummoner(){
        view?.let{
            val action = SearchSummonerFragmentDirections.actionSearchSummonerFragmentToSummonerTabFragment()
            it.findNavController().navigate(action)
        }
    }
}