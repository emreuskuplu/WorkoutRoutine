package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emre.android.workoutroutine.CalendarWorkout
import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.Event
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.data.repository.ExerciseRepo
import com.emre.android.workoutroutine.data.repository.WorkoutDayOfWeekRepo
import com.emre.android.workoutroutine.data.repository.WorkoutRepo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class CollectionWorkoutsViewModel(appDatabase: AppDatabase) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val calendarWorkout = CalendarWorkout()
    private val workoutRepo = WorkoutRepo(appDatabase)
    private val workoutDayOfWeekRepo = WorkoutDayOfWeekRepo(appDatabase)
    private val exerciseRepo = ExerciseRepo(appDatabase)
    private var updatedFutureDaysAtPosition = 20
    private var updatedPastDaysAtPosition = 20
    var viewPagerStartPosition = 18
    val monthLiveData = MutableLiveData<String>()
    var dayListLiveData = MutableLiveData<Event<Pair<Int, List<Day>>>>()

    init {
        calendarWorkout.incrementFutureTwentyDays()
        calendarWorkout.incrementPastTwentyDays()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun subscribeThirdVisibleDayObservable(thirdVisibleDayObservable: Observable<Int>) {
        thirdVisibleDayObservable
            .subscribe { thirdVisibleDayPosition ->
                monthLiveData.value = calendarWorkout
                    .dayList[thirdVisibleDayPosition]
                    .monthName
            }
            .addTo(disposables)

        /**
         * If third visible day position is reached to before five days of ending list,
         * then it'll adding future twenty days.
         * Scrolling to same place is needed because it needs to triggers the scroll
         * listener to update design of visible item positions. It needs the firstVisibleDayPosition
         * because of recyclerview only scrolls to first visible item.
         * It adds 20 to updateFutureDayListAtPosition because of list is extended.
         */
        thirdVisibleDayObservable
            .filter { thirdVisibleDayPosition ->
                thirdVisibleDayPosition >= updatedFutureDaysAtPosition + 15
            }
            .subscribe { thirdVisibleDayPosition ->
                calendarWorkout.incrementFutureTwentyDays()

                val firstVisibleDayPosition = thirdVisibleDayPosition - 2
                dayListLiveData.value =
                    Event(firstVisibleDayPosition to calendarWorkout.dayList)
                updatedFutureDaysAtPosition += 20
            }
            .addTo(disposables)

        /**
         * If third visible day position is reached to after five days of beginning list,
         * then it'll adding past twenty days.
         * (thirdVisibleDayPosition != 2): It is checks for third visible day position is not
         * beginning list after two days because of it is default start position before viewpager starts.
         * Scrolling to previous position place is needed because current position is changed via
         * head of day list added with new items. It needs the firstVisibleDayPosition because of
         * recyclerview only scrolls to first visible item.
         * It adds 20 to updateFutureDayListAtPosition because of list is extended.
         */
        thirdVisibleDayObservable
            .filter { thirdVisibleDayPosition ->
                thirdVisibleDayPosition <= updatedPastDaysAtPosition - 15 && thirdVisibleDayPosition != 2
            }
            .subscribe { thirdVisibleDayPosition ->
                calendarWorkout.incrementPastTwentyDays()

                val firstVisibleDayPosition = thirdVisibleDayPosition + 18
                dayListLiveData.value =
                    Event(firstVisibleDayPosition to calendarWorkout.dayList)
                updatedFutureDaysAtPosition += 20
            }
            .addTo(disposables)
    }

    fun getDays(): List<Day> {
        return calendarWorkout.dayList.toList()
    }
}