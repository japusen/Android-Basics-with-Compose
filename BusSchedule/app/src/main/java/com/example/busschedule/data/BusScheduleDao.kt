package com.example.busschedule.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface BusScheduleDao {
    @Query("SELECT * FROM schedule WHERE stop_name = :name ORDER BY arrival_time ASC")
    fun getStopInfo(name: String): Flow<List<BusSchedule>>

    @Query("SELECT * from schedule ORDER BY arrival_time ASC")
    fun getAllItems(): Flow<List<BusSchedule>>
}