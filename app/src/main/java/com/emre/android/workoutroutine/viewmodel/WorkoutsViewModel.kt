package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Workout
import com.emre.android.workoutroutine.data.repository.ExerciseRepo
import com.emre.android.workoutroutine.data.repository.WorkoutDayOfWeekRepo
import com.emre.android.workoutroutine.data.repository.WorkoutRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @property deleteWorkoutWithExercisesDisposable It must be cleared after
 * updateWorkoutListAfterWorkoutRemovedInDbLiveData sent for prevent memory leak.
 * @property workoutListLiveData It send value after workouts inserted in database.
 * @property updateWorkoutListAfterWorkoutRemovedInDbLiveData It send value after workouts removed in database.
 * When it sent after workouts removed, it must be called notifyItemRemoved instead of notifyDataSetChanged.
 * RecyclerView's remove animation will be broken if notifyDataSetChanged is called.
 */
class WorkoutsViewModel(appDatabase: AppDatabase) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val workoutRepo = WorkoutRepo(appDatabase)
    private val workoutDayOfWeekRepo = WorkoutDayOfWeekRepo(appDatabase)
    private val exerciseRepo = ExerciseRepo(appDatabase)
    val deleteWorkoutWithExercisesDisposable = CompositeDisposable()
    val workoutListLiveData = MutableLiveData<List<Pair<Workout, List<Exercise>>>>()
    val updateWorkoutListAfterWorkoutRemovedInDbLiveData = MutableLiveData<Pair<Int, Int>>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun subscribeWorkoutsWithExercisesInDb(day: Day) {
        workoutRepo.getWorkoutsWithExercisesInDbObservable(day, exerciseRepo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { workoutsWithExercises ->
                workoutListLiveData.value = workoutsWithExercises
            }
            .addTo(disposables)
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
                workoutDayOfWeekRepo.deleteAllMatchWorkoutIdInDb(workoutId)
                exerciseRepo.deleteAllMatchWorkoutIdInDb(workoutId)
                positionForRemoveWorkoutInList to workoutListSize
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (positionForRemoveWorkoutInList, workoutListSize) ->
                updateWorkoutListAfterWorkoutRemovedInDbLiveData.value =
                    positionForRemoveWorkoutInList to workoutListSize

            }
            .addTo(deleteWorkoutWithExercisesDisposable)
    }
}