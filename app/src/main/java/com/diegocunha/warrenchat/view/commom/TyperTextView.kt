package com.diegocunha.warrenchat.view.commom

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView


class TyperTextView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    lateinit var textSequence: CharSequence
    private var index: Int = 0
    private var delay: Long = 50
    private val handlerDelay = Handler()

    private val characterAdder = object : Runnable {
        override fun run() {
            text = textSequence.subSequence(0, index++)

            if (index <= textSequence.length) {
                handlerDelay.postDelayed(this, delay)
            }
        }
    }

    fun animateText(txt: CharSequence) {
        textSequence = txt
        index = 0
        text = null
        handlerDelay.removeCallbacks(characterAdder)
        handlerDelay.postDelayed(characterAdder, delay)
    }

}