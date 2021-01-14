package com.silverthread.tftstatistics.ui.main

import com.silverthread.tftstatistics.R
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val summonerViewModel: SummonerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Navigation.findNavController(this, R.id.nav_host_fragment)

        summonerViewModel.progressData.observe(this, Observer {
            progress.visibility = it
        })
    }
}