package com.example.playersreviewsapplication.landing.show

import com.example.playersreviewsapplication.data.response.Player

interface OnPlayerClickListener {
    fun onPlayerPressed(player: Player)
}