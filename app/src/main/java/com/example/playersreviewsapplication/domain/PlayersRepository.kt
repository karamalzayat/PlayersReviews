package com.example.playersreviewsapplication.domain

import com.example.playersreviewsapplication.data.database.PlayerDAO
import com.example.playersreviewsapplication.data.database.PlayerEntity
import com.example.playersreviewsapplication.data.network.CommunicationAPI
import com.example.playersreviewsapplication.data.network.RetrofitHelper
import com.example.playersreviewsapplication.data.response.Player

class PlayersRepository(private val playerDAO: PlayerDAO) {
    private val communicationAPI =
        RetrofitHelper.getInstance().create(CommunicationAPI::class.java)

    private var players: ArrayList<Player> = arrayListOf()

    suspend fun gettingPlayersFromServer(): List<Player> {
        // this function fetches players from server
        // depend on these data we detect which action will be in ViewModel
        return try {
            communicationAPI.getPlayers().body()!!.athletes!!.ifEmpty {
                arrayListOf()
            }
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun puttingPlayersToDB(players: List<Player>) {
        // this method used for putting fetched players from server to local DB
        try {
            for (player in players) {
                playerDAO.insertPlayer(
                    PlayerEntity(
                        playerName = player.name.orEmpty(),
                        brief = player.brief.orEmpty(),
                        PlayerImage = player.image.orEmpty(),
                    )
                )
            }
        } catch (e: Exception) {

        }
    }

    suspend fun gettingPlayersFromDB(): ArrayList<Player> {
        // this method gets players from data base
        return try {
            for (player in playerDAO.getPlayers()) {
                players.add(
                    Player(
                        brief = player.brief,
                        name = player.playerName,
                        image = player.PlayerImage
                    )
                )
            }
            players
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun deletePlayersFromDB() {
        // this method delete All players from DB
        try {
            playerDAO.deletePlayers()
        } catch (e: Exception) {

        }
    }
}