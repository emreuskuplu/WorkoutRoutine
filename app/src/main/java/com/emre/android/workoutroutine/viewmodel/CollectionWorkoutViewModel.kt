package com.emre.android.workoutroutine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.Repository
import com.emre.android.workoutroutine.data.model.Workout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.Calendar
import java.util.Locale

class CollectionWorkoutViewModel(
    application: Application,
    thirdVisibleDayObservable: Observable<Int>,
    newWorkoutButtonObservable: Observable<Unit>
) : AndroidViewModel(application) {

    private val dayList: MutableList<Day> = mutableListOf()
    private val disposables = CompositeDisposable()
    private val calendarPast = Calendar.getInstance()
    private val calendarFuture = Calendar.getInstance()
    private val repository = Repository(application.applicationContext)
    private var updatedFutureDayListAtPosition = 20
    private var updatedPastDayListAtPosition = 20
    private var workoutsInDbObservable = repository.workoutsInDbObservable()
    private val updateWorkoutListSubject = PublishSubject.create<Unit>()
    val monthLiveData = MutableLiveData<String>()
    val dayListLiveData = MutableLiveData<Pair<Int, List<Day>>>()
    val workoutListLiveData = MutableLiveData<List<Pair<Workout, List<Exercise>>>>()

    init {
        addFutureTwentyDays()
        addPastTwentyDays()

        /**
         * updateWorkoutListSubject in use for trigger workoutsInDbObservable
         * Schedulers.single is necessary for synchronize working with other observables that in use this single thread,
         * because of all exercises must be added before this observable fetch exercises.
         */
        Observables.combineLatest(updateWorkoutListSubject, workoutsInDbObservable)
            .observeOn(Schedulers.single())
            .subscribeOn(AndroidSchedulers.mainThread())
            .map {
                val workoutListWithExercises = mutableListOf<Pair<Workout, List<Exercise>>>()

                for (workout in it.second) {
                    workoutListWithExercises.add(workout to repository.getExercisesInDb(workout.id!!))
                }

                workoutListWithExercises.toList()
            }
            .subscribe {
                workoutListLiveData.postValue(it)
            }
            .addTo(disposables)

        thirdVisibleDayObservable
            .subscribe { thirdVisibleDayPosition ->
                updateWorkoutListSubject.onNext(Unit)
                monthLiveData.postValue(dayList[thirdVisibleDayPosition].monthName)
            }
            .addTo(disposables)

        /**
         * If third visible day position is reached to before five days of ending list,
         * then it'll adding future twenty days.
         * It adds 20 to updateFutureDayListAtPosition because of list is extended.
         */
        thirdVisibleDayObservable
            .filter { it >= updatedFutureDayListAtPosition + 15 }
            .subscribe {
                addFutureTwentyDays()
                dayListLiveData.postValue(it - 2 to dayList)
                updatedFutureDayListAtPosition += 20
            }
            .addTo(disposables)

        /**
         * If third visible day position is reached to after five days of beginning list,
         * then it'll adding past twenty days.
         * It is checks for third visible day position is not beginning list after two days,
         * because of it is default start position before viewpager starts.
         * It adds 20 to updateFutureDayListAtPosition because of list is extended.
         */
        thirdVisibleDayObservable
            .filter { it <= updatedPastDayListAtPosition - 15 && it != 2 }
            .subscribe {
                addPastTwentyDays()
                dayListLiveData.postValue(it + 18 to dayList)
                updatedFutureDayListAtPosition += 20
            }
            .addTo(disposables)

        newWorkoutButtonObservable
            .observeOn(Schedulers.single())
            .subscribe {
                val id = repository.addWorkout(
                    Workout(
                        workoutName = "Strength Workout",
                        workoutDateStart = "26.04.2021"
                    )
                )

                repository.addExercise(
                    Exercise(
                        workoutId = id,
                        exerciseName = "Jump rope",
                        exerciseType = "based on set"
                    )
                )
                repository.addExercise(
                    Exercise(
                        workoutId = id,
                        exerciseName = "Bodyweight Squats",
                        exerciseType = "based on set"
                    )
                )
                repository.addExercise(
                    Exercise(
                        workoutId = id,
                        exerciseName = "Bench Press",
                        exerciseType = "based on set"
                    )
                )
                repository.addExercise(
                    Exercise(
                        workoutId = id,
                        exerciseName = "Push Ups",
                        exerciseType = "based on set"
                    )
                )
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getDays(): List<Day> {
        return dayList.toList()
    }

    private fun addFutureTwentyDays() {
        val futureTwentyDayList = mutableListOf<Day>()

        for (i in 0 until 20) {
            val dayNumber = calendarFuture.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendarFuture.getDisplayName(
                Calendar.DAY_OF_WEEK,
                Calendar.SHORT,
                Locale.getDefault()
            )
            val monthName =
                calendarFuture.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

            futureTwentyDayList.add(
                i, Day(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: ""
                )
            )

            calendarFuture.add(Calendar.DAY_OF_MONTH, 1)
        }

        dayList.addAll(futureTwentyDayList)
    }

    private fun addPastTwentyDays() {
        val pastTwentyDayList = mutableListOf<Day>()

        calendarPast.add(Calendar.DAY_OF_MONTH, -1)

        for (i in 0 until 20) {
            val dayNumber = calendarPast.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendarPast.getDisplayName(
                Calendar.DAY_OF_WEEK,
                Calendar.SHORT,
                Locale.getDefault()
            )
            val monthName =
                calendarPast.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

            pastTwentyDayList.add(
                0, Day(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: ""
                )
            )

            calendarPast.add(Calendar.DAY_OF_MONTH, -1)
        }

        dayList.addAll(0, pastTwentyDayList)
    }
}