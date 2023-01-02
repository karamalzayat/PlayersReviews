package com.example.playersreviewsapplication.data.response


data class PlayerResponse(val athletes: List<Player>?)

data class Player(
    val name:String?,
    val image:String?,
    val brief:String?
)
