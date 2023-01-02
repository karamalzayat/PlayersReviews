package com.example.playersreviewsapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDAO {

    @Query("SELECT * FROM PlayerEntity")
   suspend fun getPlayers(): List<PlayerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(vararg user: PlayerEntity)

    @Query("DELETE FROM PlayerEntity")
    suspend fun deletePlayers()
}