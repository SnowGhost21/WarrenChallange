package com.diegocunha.warrenchat.view.commom

import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

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

    fun configureToolbar(toolbar: Toolbar) {
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(toolbar)
            configureToolbarHeight(toolbar)
        }


    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        return result
    }
}