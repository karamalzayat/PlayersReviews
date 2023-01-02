package com.example.playersreviewsapplication.data.network

import com.example.playersreviewsapplication.data.response.PlayerResponse
import retrofit2.http.*

interface CommunicationAPI {
    @GET("/Bassem-Samy/f227855df4d197d3737c304e9377c4d4/raw")
    suspend fun getPlayers(): retrofit2.Response<PlayerResponse>
}