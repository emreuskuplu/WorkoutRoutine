package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emre.android.workoutroutine.CalendarWorkout
import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.Event
import com.emre.android.workoutroutine.data.repository.ExerciseRepo
import com.emre.android.workoutroutine.data.repository.WorkoutRepo
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Workout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @property deleteWorkoutWithExercisesDisposable It must be cleared after
 * updateWorkoutListAfterWorkoutRemovedInDbLiveData posted for prevent memory leak.
 * @property workoutListLiveData It post value after workouts inserted in database.
 * @property updateWorkoutListAfterWorkoutRemovedInDbLiveData It post value after workouts removed in database.
 * When it posted after workouts removed, it must be called notifyItemRemoved instead of notifyDataSetChanged.
 * RecyclerView's remove animation will be broken if notifyDataSetChanged is called.
 */
class CollectionWorkoutsViewModel(appDatabase: AppDatabase) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val calendarWorkout = CalendarWorkout()
    private val workoutRepo = WorkoutRepo(appDatabase)
    private val exerciseRepo = ExerciseRepo(appDatabase)
    private var updatedFutureDaysAtPosition = 20
    private var updatedPastDaysAtPosition = 20
    var viewPagerStartPosition = 18
    val deleteWorkoutWithExercisesDisposable = CompositeDisposable()
    val monthLiveData = MutableLiveData<String>()
    var dayListLiveData = MutableLiveData<Event<Pair<Int, List<Day>>>>()
    val workoutListLiveData = MutableLiveData<List<Pair<Workout, List<Exercise>>>>()
    val updateWorkoutListAfterWorkoutRemovedInDbLiveData = MutableLiveData<Pair<Int, Int>>()

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
         * It is checks for third visible day position is not beginning list after two days
         * because of it is default start position before viewpager starts.
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

    fun subscribeNewWorkoutObservable(newWorkoutButtonObservable: Observable<Unit>) {
        newWorkoutButtonObservable
            .observeOn(Schedulers.single())
            .subscribe {
                val workoutId = workoutRepo.insertToDb(
                    Workout(
                        workoutName = "Strength Workout",
                        workoutDateStart = "2021.03.01"
                    )
                )

                exerciseRepo.insertToDb(
                    Exercise(
                        workoutId = workoutId,
                        exerciseName = "Jump rope",
                        exerciseType = "based on set"
                    )
                )
                exerciseRepo.insertToDb(
                    Exercise(
                        workoutId = workoutId,
                        exerciseName = "Bodyweight Squats",
                        exerciseType = "based on set"
                    )
                )
                exerciseRepo.insertToDb(
                    Exercise(
                        workoutId = workoutId,
                        exerciseName = "Bench Press",
                        exerciseType = "based on set"
                    )
                )
                exerciseRepo.insertToDb(
                    Exercise(
                        workoutId = workoutId,
                        exerciseName = "Push Ups",
                        exerciseType = "based on set"
                    )
                )
            }
            .addTo(disposables)
    }

    /**
     * It needs to delay emitting because of workout possibly deleted in db and if workout
     * deleted in db, then item remove animation of recyclerview break without delay because of
     * notifyDataSetChanged called after workoutListLiveData send value.
     */
    fun subscribeWorkoutsWithExercisesInDb(day: Day): Disposable {
        return workoutRepo.getWorkoutsWithExercisesInDbObservable(day, exerciseRepo)
            .delay(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { workoutsWithExercises ->
                workoutListLiveData.value = workoutsWithExercises
            }
    }

    /**
     * This method is called from DeleteWorkoutDialog.
     * It must be add disposable in this class instead of in Dialog.
     * Because of dialog must be dismissed after called this method.
     * If disposable added in dialog, then it possibly break the process of subscriber.
     * Disposable will clear after observer of updateWorkoutListAfterWorkoutRemovedInDbLiveData
     * finished.
     */
    fun subscribeDeleteWorkoutWithExercisesInside(
        deleteWorkoutWithExercisesInsideObservable: Observable<Pair<Pair<Int, Long>, Int>>
    ) {
        deleteWorkoutWithExercisesInsideObservable
            .observeOn(Schedulers.single())
            .map { (positionForRemoveWorkoutInListAndWorkoutId, workoutListSize) ->
                val positionForRemoveWorkoutInList =
                    positionForRemoveWorkoutInListAndWorkoutId.first
                val workoutId = positionForRemoveWorkoutInListAndWorkoutId.second

                workoutRepo.deleteInDb(workoutId)
                exerciseRepo.deleteAllMatchWorkoutIdInDb(workoutId)

                positionForRemoveWorkoutInList to workoutListSize
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (positionForRemoveWorkoutInList, workoutListSize) ->
                updateWorkoutListAfterWorkoutRemovedInDbLiveData.value =
                    positionForRemoveWorkoutInList to workoutListSize

            }.addTo(deleteWorkoutWithExercisesDisposable)
    }

    fun getDays(): List<Day> {
        return calendarWorkout.dayList.toList()
    }
}