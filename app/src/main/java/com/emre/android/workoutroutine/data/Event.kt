package com.emre.android.workoutroutine.data

/**
 * It is in use for wrapping livedata values for prevent get values that already been send.
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 *
 * If observer of Livedata is initialized when livedata has value, then observer of livedata automatically
 * gets last value when it's initialized. It may causes some bugs.
 *
 * Prevented bug:
 * When observer of daysLiveData gets value for scrolling to previous place after past days added
 * and if fragment is recreated like user selects history page and then selects workout page.
 * Then observer of daysLiveData in fragment re-initialized and it gets last value and it
 * scrolls to previous place again.
 */
class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}