package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM Airport " +
            "WHERE iata_code LIKE '%:query%' OR name LIKE '%:query%' " +
            "ORDER BY passengers DESC")
    fun getAutoComplete(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM Airport " +
            "WHERE iata_code != :iataCode " +
            "ORDER BY passengers DESC")
    fun getFlights(iataCode: String): Flow<List<Airport>>
}