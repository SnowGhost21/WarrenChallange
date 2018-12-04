package com.diegocunha.warrenchat.view.commom

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView
import com.diegocunha.warrenchat.extensions.mutableLiveDataOf


class TyperTextView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    lateinit var textSequence: CharSequence
    private var index: Int = 0
    private var delay: Long = 50
    private val handlerDelay = Handler()
    val _isFinished = mutableLiveDataOf(false)
    var textSequenceToDelete: CharSequence = ""
    var isFinished = false

    private val characterController = object : Runnable {
        override fun run() {
            if (!textSequenceToDelete.isEmpty()) {
                if (index <= textSequenceToDelete.length && !isFinished) {
                    text = textSequenceToDelete.subSequence(0, index++)
                    handlerDelay.postDelayed(this, delay)
                    isFinished = index == textSequenceToDelete.length
                    _isFinished.postValue(false)
                } else {
                    val txt = textSequenceToDelete.substring(0, index--)
                    textSequenceToDelete = txt
                    text = txt
                    handlerDelay.postDelayed(this, delay)
                }

                if (index < 0) {
                    index = 0
                }


            } else {
                text = textSequence.subSequence(0, index++)

                if (index <= textSequence.length) {
                    handlerDelay.postDelayed(this, delay)
                    _isFinished.postValue(false)
                }

                if (index == textSequence.length) {
                    _isFinished.postValue(true)
                }
            }


        }
    }

    fun animateText(txt: CharSequence) {
        if (txt.contains("<erase>")) {
            textSequenceToDelete = txt.split("<erase>")[0].trim()
            textSequence = txt.split("<erase>")[1].trim()
        } else {
            textSequence = txt
        }

        index = 0
        text = null
        handlerDelay.removeCallbacks(characterController)
        handlerDelay.postDelayed(characterController, delay)
    }

}