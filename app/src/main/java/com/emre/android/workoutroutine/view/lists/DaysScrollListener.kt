package com.emre.android.workoutroutine.view.lists

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.lists.adapters.DaysAdapter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * @param linearLayoutManager This layout manager must be same with recyclerview layout manager for
 * get recyclerview item positions.
 */
class DaysScrollListener(private val linearLayoutManager: LinearLayoutManager,
                         context: Context): RecyclerView.OnScrollListener() {

    private val colorFirstAndFifthDay = ContextCompat.getColor(context, R.color.colorFirstAndFifthDay)
    private val colorSecondAndFourthDay = ContextCompat.getColor(context, R.color.colorSecondAndFourthDay)
    private val colorThirdDay = ContextCompat.getColor(context, R.color.colorSecondaryVariant)
    private val thirdVisibleDaySubject = PublishSubject.create<Int>()

    val thirdVisibleDayObservable: Observable<Int> = thirdVisibleDaySubject.hide()

    /**
     * After first visible item is equal to first completely visible item then it updates
     * design of days and fetch workouts in database.
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItemPosition =
            linearLayoutManager.findFirstVisibleItemPosition()
        val firstCompletelyVisibleItemPosition =
            linearLayoutManager.findFirstCompletelyVisibleItemPosition()

        if (firstVisibleItemPosition == firstCompletelyVisibleItemPosition) {

            for (i in 0..4) {
                val viewHolder = recyclerView
                    .findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition + i)
                        as DaysAdapter.DayViewHolder

                if (i != 2) {
                    viewHolder.binding.workoutDayNumber.textSize = 20F
                    viewHolder.binding.workoutDayName.visibility = View.INVISIBLE
                } else {
                    viewHolder.binding.workoutDayNumber.textSize = 25F
                    viewHolder.binding.workoutDayName.visibility = View.VISIBLE
                }

                if (i == 0 || i == 4) {
                    viewHolder.binding.workoutDayNumber.setTextColor(colorFirstAndFifthDay)
                } else if (i == 1 || i == 3) {
                    viewHolder.binding.workoutDayNumber.setTextColor(colorSecondAndFourthDay)
                } else if (i == 2) {
                    viewHolder.binding.workoutDayNumber.setTextColor(colorThirdDay)
                }
            }

            thirdVisibleDaySubject.onNext(firstCompletelyVisibleItemPosition + 2)

            Log.i(this.javaClass.simpleName, "${firstCompletelyVisibleItemPosition + 2}")
        }
    }
}