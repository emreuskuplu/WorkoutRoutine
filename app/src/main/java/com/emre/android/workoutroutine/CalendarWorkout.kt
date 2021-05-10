package com.emre.android.workoutroutine

import android.util.Log
import com.emre.android.workoutroutine.data.model.Day
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarWorkout {
    private val calendarPast = Calendar.getInstance()
    private val calendarFuture = Calendar.getInstance()
    val dayList = mutableListOf<Day>()

    init {
        calendarPast.add(Calendar.DAY_OF_MONTH, -1)
    }

    fun incrementFutureTwentyDays() {
        val futureTwentyDayList = mutableListOf<Day>()

        for (i in 0 until 20) {
            val dayNumber = calendarFuture.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendarFuture.getDisplayName(
                Calendar.DAY_OF_WEEK,
                Calendar.SHORT,
                Locale.getDefault()
            )
            val monthName = calendarFuture.getDisplayName(
                Calendar.MONTH,
                Calendar.LONG,
                Locale.getDefault()
            )
            val dateFuture = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                .format(calendarFuture.time)

            Log.i("Date future", dateFuture)

            futureTwentyDayList.add(
                i, Day(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: "",
                    dateFuture
                )
            )

            calendarFuture.add(Calendar.DAY_OF_MONTH, 1)
        }

        dayList.addAll(futureTwentyDayList)
    }

    fun incrementPastTwentyDays() {
        val pastTwentyDayList = mutableListOf<Day>()

        for (i in 0 until 20) {
            val dayNumber = calendarPast.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendarPast.getDisplayName(
                Calendar.DAY_OF_WEEK,
                Calendar.SHORT,
                Locale.getDefault()
            )
            val monthName = calendarPast.getDisplayName(
                Calendar.MONTH,
                Calendar.LONG,
                Locale.getDefault()
            )
            val datePast = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                .format(calendarPast.time)

            Log.i("Date past", datePast)

            pastTwentyDayList.add(
                0, Day(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: "",
                    datePast
                )
            )

            calendarPast.add(Calendar.DAY_OF_MONTH, -1)
        }

        dayList.addAll(0, pastTwentyDayList)
    }
}