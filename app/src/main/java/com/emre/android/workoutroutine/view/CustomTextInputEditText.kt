package com.emre.android.workoutroutine.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import com.google.android.material.textfield.TextInputEditText

class CustomTextInputEditText(context: Context, attrs: AttributeSet) :
    TextInputEditText(context, attrs) {

    override fun onEditorAction(actionCode: Int) {
        super.onEditorAction(actionCode)
        isFocusable = false
        isFocusableInTouchMode = true
        hideKeyboard()
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isFocusable = false
            isFocusableInTouchMode = true
        }
        return super.onKeyPreIme(keyCode, event)
    }
}