package com.example.playersreviewsapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PlayerEntity(
    @ColumnInfo(name = "player_name")
    var playerName: String,
    @ColumnInfo(name = "player_image_url")
    var PlayerImage: String,
    @ColumnInfo(name = "brief")
    var brief: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}