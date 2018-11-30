package com.diegocunha.warrenchat.view.commom

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

open class BaseActivity: AppCompatActivity() {


    private fun configureToolbarHeight(toolbar: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val layoutParams = toolbar.layoutParams
            layoutParams.height += getStatusBarHeight()

            toolbar.layoutParams = layoutParams
            toolbar.setPadding(
                    toolbar.paddingLeft, toolbar.paddingTop + getStatusBarHeight(),
                    toolbar.paddingRight, toolbar.paddingBottom
            )
        }
    }

    protected fun configureViewHeight(fragment: Fragment) {
        val view = fragment.view


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
            val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.setMargins(0, 0, 0, -(getStatusBarHeight() *2))
            view.layoutParams = layoutParams

            view.setPadding(
                    view.paddingLeft, view.paddingTop - (getStatusBarHeight() /3),
                    view.paddingRight, view.paddingBottom
            )
        }
    }

    fun configureToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        configureToolbarHeight(toolbar)
    }

    protected fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        return result
    }


}