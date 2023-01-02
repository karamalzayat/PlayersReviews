package com.example.playersreviewsapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayerEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun playerDatabaseDao(): PlayerDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(AppDataBase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


