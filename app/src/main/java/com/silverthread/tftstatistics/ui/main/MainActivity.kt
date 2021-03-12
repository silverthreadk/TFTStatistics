package com.silverthread.tftstatistics.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.databinding.ActivityMainBinding
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel
import com.silverthread.tftstatistics.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val summonerViewModel: SummonerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        summonerViewModel.notFoundLiveData.observe(this, EventObserver {
            Toast.makeText(this, R.string.message_not_found, Toast.LENGTH_LONG).show()
        })
        summonerViewModel.progressLiveData.observe(this, Observer {
            binding.progress.visibility = it
        })
    }
}