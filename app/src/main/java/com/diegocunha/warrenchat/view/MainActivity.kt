package com.diegocunha.warrenchat.view

import android.os.Bundle
import com.diegocunha.warrenchat.R
import com.diegocunha.warrenchat.view.commom.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_main)
        configureToolbar(toolbar)
        configureViewHeight(fragment)
    }
}
