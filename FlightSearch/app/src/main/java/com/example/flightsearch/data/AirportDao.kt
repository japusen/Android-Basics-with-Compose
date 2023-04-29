package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM Airport WHERE iata_code = :iataCode")
    fun getAirport(iataCode: String): Flow<Airport>

    @Query("SELECT * FROM Airport")
    fun getAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM Airport " +
            "WHERE iata_code LIKE '%' || :search_query || '%' " +
            "OR name LIKE '%' || :search_query || '%' " +
            "ORDER BY passengers DESC")
    fun getAutoComplete(search_query: String): Flow<List<Airport>>

    @Query("SELECT * FROM Airport " +
            "WHERE iata_code != :iataCode " +
            "ORDER BY passengers DESC")
    fun getFlights(iataCode: String): Flow<List<Airport>>
}