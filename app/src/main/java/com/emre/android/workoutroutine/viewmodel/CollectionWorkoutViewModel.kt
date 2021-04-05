package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emre.android.workoutroutine.Day
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.Calendar
import java.util.Locale

class CollectionWorkoutViewModel(
    thirdVisibleDayObservable: Observable<Int>) : ViewModel() {

    private val dayList: MutableList<Day> = mutableListOf()
    private val disposables = CompositeDisposable()
    private val calendarPast = Calendar.getInstance()
    private val calendarFuture = Calendar.getInstance()
    private var updatedFutureDayListAtPosition = 20
    private var updatedPastDayListAtPosition = 20
    val monthLiveData = MutableLiveData<String>()
    val dayListLiveData = MutableLiveData<Pair<Int, List<Day>>>()

    init {
        thirdVisibleDayObservable
                .subscribe { thirdVisibleDayPosition ->
                    monthLiveData.postValue(dayList[thirdVisibleDayPosition].monthName)
                }
                .addTo(disposables)

        thirdVisibleDayObservable
                .filter { it >= updatedFutureDayListAtPosition + 15 }
                .subscribe {
                    addFutureTwentyDays()
                    dayListLiveData.postValue(it - 2 to dayList)
                    updatedFutureDayListAtPosition += 15
                }
                .addTo(disposables)

        thirdVisibleDayObservable
                .filter { it <= updatedPastDayListAtPosition - 15 && it != 2}
                .subscribe {
                    addPastTwentyDays()
                    dayListLiveData.postValue(it + 18 to dayList)
                    updatedFutureDayListAtPosition += 20
                }
                .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getDays(): List<Day> {
        addFutureTwentyDays()
        addPastTwentyDays()
        return dayList.toList()
    }

    private fun addFutureTwentyDays() {
        val futureFiftyDayList = mutableListOf<Day>()

        for (i in 0 until 20) {
            val dayNumber = calendarFuture.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendarFuture.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            val monthName = calendarFuture.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

            futureFiftyDayList.add(i, Day(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: ""))

            calendarFuture.add(Calendar.DAY_OF_MONTH, 1)
        }

        dayList.addAll(futureFiftyDayList)
    }

    private fun addPastTwentyDays() {
        val pastFiftyDayList = mutableListOf<Day>()

        calendarPast.add(Calendar.DAY_OF_MONTH, -1)

        for (i in 0 until 20) {
            val dayNumber = calendarPast.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendarPast.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            val monthName = calendarPast.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

            pastFiftyDayList.add(0, Day(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: ""))

            calendarPast.add(Calendar.DAY_OF_MONTH, -1)
        }

        dayList.addAll(0, pastFiftyDayList)
    }
}