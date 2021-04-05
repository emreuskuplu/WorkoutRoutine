package com.emre.android.workoutroutine.view.lists

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class DayListScrollListener(private val linearLayoutManager: LinearLayoutManager,
                            context: Context?): RecyclerView.OnScrollListener() {

    private val colorFirstAndFifthDay = ContextCompat.getColor(context!!, R.color.colorFirstAndFifthDay)
    private val colorSecondAndFourthDay = ContextCompat.getColor(context!!, R.color.colorSecondAndFourthDay)
    private val colorThirdDay = ContextCompat.getColor(context!!, R.color.colorSecondaryVariant)
    private val thirdVisibleDaySubject = PublishSubject.create<Int>()
    val thirdVisibleDayObservable: Observable<Int> = thirdVisibleDaySubject.hide()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition()

        if (firstVisibleItemPosition == firstCompletelyVisibleItemPosition) {
            val firstVisibleViewHolder = recyclerView.findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition)!!
            val secondVisibleViewHolder = recyclerView.findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition + 1)!!
            val thirdVisibleViewHolder = recyclerView.findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition + 2)!!
            val fourthVisibleViewHolder = recyclerView.findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition + 3)!!
            val fifthVisibleViewHolder = recyclerView.findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition + 4)!!

            val firstVisibleDayNumber: TextView = firstVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_number)
            val firstVisibleDayName: TextView = firstVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_name)

            val secondVisibleDayNumber: TextView = secondVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_number)
            val secondVisibleDayName: TextView = secondVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_name)

            val thirdVisibleDayNumber: TextView = thirdVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_number)
            val thirdVisibleDayName: TextView = thirdVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_name)

            val fourthVisibleDayNumber: TextView = fourthVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_number)
            val fourthVisibleDayName: TextView = fourthVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_name)

            val fifthVisibleDayNumber: TextView = fifthVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_number)
            val fifthVisibleDayName: TextView = fifthVisibleViewHolder
                    .itemView.findViewById(R.id.workout_day_name)

            firstVisibleDayNumber.textSize = 20F
            secondVisibleDayNumber.textSize = 20F
            thirdVisibleDayNumber.textSize = 25F
            fourthVisibleDayNumber.textSize = 20F
            fifthVisibleDayNumber.textSize = 20F

            firstVisibleDayNumber.setTextColor(colorFirstAndFifthDay)
            secondVisibleDayNumber.setTextColor(colorSecondAndFourthDay)
            thirdVisibleDayNumber.setTextColor(colorThirdDay)
            fourthVisibleDayNumber.setTextColor(colorSecondAndFourthDay)
            fifthVisibleDayNumber.setTextColor(colorFirstAndFifthDay)

            firstVisibleDayName.visibility = View.INVISIBLE
            secondVisibleDayName.visibility = View.INVISIBLE
            thirdVisibleDayName.visibility = View.VISIBLE
            fourthVisibleDayName.visibility = View.INVISIBLE
            fifthVisibleDayName.visibility = View.INVISIBLE

            thirdVisibleDaySubject.onNext(firstCompletelyVisibleItemPosition + 2)

            Log.i("DayListScrollListener", "${firstCompletelyVisibleItemPosition + 2}")
        }
    }
}