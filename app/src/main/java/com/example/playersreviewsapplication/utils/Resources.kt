package com.example.playersreviewsapplication.utils

import com.example.playersreviewsapplication.data.response.Player


sealed class Resources<T>(
    val data: T? = null,
    val error: CustomCommands = CustomCommands.SomethingWentWrong("Something Went Wrong")
) {
    class Success<T>(data: T?) : Resources<T>(data)
    class Loading<T> : Resources<T>()
    class Error<T>(customCommands: CustomCommands) : Resources<T>(error = customCommands)
    sealed class CustomCommands(
        var message: String = "",
        var player: Player = Player("", "", "")
    ) {

        object OpenDetailsActivity : CustomCommands()
        object LoadPlayers : CustomCommands()
        object ShowPlayers : CustomCommands()
        object ShowReloadBTN : CustomCommands()

        data class SomethingWentWrong(val error: String) : CustomCommands(message = error)


    }
}