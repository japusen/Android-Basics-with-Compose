package com.example.busschedule.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface BusScheduleDao {
    @Query("SELECT * FROM BusSchedule WHERE stopName = :name ORDER BY arrivalTimeInMillis DESC")
    fun getStopInfo(name: String)

    @Query("SELECT * from BusSchedule ORDER BY arrivalTimeInMillis DESC")
    fun getAllItems(): Flow<List<BusSchedule>>
}