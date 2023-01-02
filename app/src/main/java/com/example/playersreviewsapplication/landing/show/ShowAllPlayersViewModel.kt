package com.example.playersreviewsapplication.landing.show

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersreviewsapplication.data.database.AppDataBase
import com.example.playersreviewsapplication.data.response.Player
import com.example.playersreviewsapplication.domain.PlayersRepository
import com.example.playersreviewsapplication.utils.Resources
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ShowAllPlayersViewModel(application: Application) : AndroidViewModel(application) {
    var showPlayersFlow: MutableSharedFlow<Resources.CustomCommands> = MutableSharedFlow()
    private val playerDAO = AppDataBase.getInstance(application).playerDatabaseDao()
    private var repository: PlayersRepository = PlayersRepository(playerDAO)

    var playersList: ArrayList<Player> = arrayListOf()


    init {
        loadPlayers()
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            showPlayersFlow.emit(Resources.CustomCommands.LoadPlayers)
            try {
                val players = repository.gettingPlayersFromServer()
                if (players.isNotEmpty()) {
                    repository.deletePlayersFromDB()
                    repository.puttingPlayersToDB(players)
                    playersList = repository.gettingPlayersFromDB()
                    showPlayersFlow.emit(Resources.CustomCommands.ShowPlayers)
                } else {
                    playersList = repository.gettingPlayersFromDB()
                    if (playersList.isNotEmpty()) {
                        showPlayersFlow.emit(Resources.CustomCommands.ShowPlayers)
                    } else {
                        showPlayersFlow.emit(Resources.CustomCommands.ShowReloadBTN)
                    }
                }
            } catch (e: Exception) {
                showPlayersFlow.emit(Resources.CustomCommands.ShowReloadBTN)
            }
        }
    }

    fun reload() {
        loadPlayers()
    }

    fun onPlayerPressed(player: Player) {
        viewModelScope.launch {
            try {
                Resources.CustomCommands.OpenDetailsActivity.player = player
                showPlayersFlow.emit(Resources.CustomCommands.OpenDetailsActivity)
            } catch (e: Exception) {
            }
        }
    }

}