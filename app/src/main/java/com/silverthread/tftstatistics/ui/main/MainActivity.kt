package com.silverthread.tftstatistics.ui.main

import com.silverthread.tftstatistics.R
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.silverthread.tftstatistics.databinding.ActivityMainBinding
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val summonerViewModel: SummonerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Navigation.findNavController(this, R.id.nav_host_fragment)

        summonerViewModel.progressData.observe(this, Observer {
            binding.progress.visibility = it
        })
    }
}