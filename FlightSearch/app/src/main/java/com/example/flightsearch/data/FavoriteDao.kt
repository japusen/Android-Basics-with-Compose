package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM Favorite")
    fun getFavorites(): Flow<List<Favorite>>

    @Insert(
        entity = Favorite::class,
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insert(departureCode: String, destinationCode: String)
}