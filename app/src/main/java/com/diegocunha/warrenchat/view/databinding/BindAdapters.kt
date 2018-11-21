package com.diegocunha.warrenchat.view.databinding

import android.text.InputType
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun visibleOrGone(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("updateInputType")
fun setInputType(view: EditText, type: String) {
    when(type) {
        "number" -> view.inputType = InputType.TYPE_CLASS_NUMBER
        else -> view.inputType = InputType.TYPE_CLASS_NUMBER
    }
}