package com.example.playersreviewsapplication.landing.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playersreviewsapplication.R
import com.example.playersreviewsapplication.databinding.ActivityPlayerDetailsBinding

class PlayerDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_details)
        binding.lifecycleOwner = this
        val mViewModel = ViewModelProvider(this)[PlayerDetailsViewModel::class.java]
        binding.playerDetailsViewModel = mViewModel
        binding.playerName.text =
            intent.getBundleExtra("player")!!.getString("playerName")
        binding.playerBrief.text =
            intent.getBundleExtra("player")!!.getString("playerBrief")
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(this)
            .load(intent.getBundleExtra("player")!!.getString("PlayerImage"))
            .apply(requestOptions)
            .skipMemoryCache(false)//for caching the image url in case phone is offline
            .into(binding.playerImage)
    }
}