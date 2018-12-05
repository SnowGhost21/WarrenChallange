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
    var isDeleteFinished = false
    var isCreated = false

    private val characterController = object : Runnable {
        override fun run() {
            if (!textSequenceToDelete.isEmpty()) {
                removeCharacter(this)
            } else {
                addCharacter(this)
            }
        }
    }

    fun createText(txt: CharSequence) {
        if (!isCreated) {
            animateText(txt)
        } else {
            createSimpleText(txt)
        }
    }

    private fun animateText(txt: CharSequence) {
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


    private fun createSimpleText(txt: CharSequence) {
        textSequence = if (txt.contains("<erase>")) {
            txt.split("<erase>")[1].trim()
        } else {
            txt
        }

        text = textSequence
    }

    private fun removeCharacter(runnable: Runnable) {
        if (index <= textSequenceToDelete.length && !isDeleteFinished) {
            text = textSequenceToDelete.subSequence(0, index++)
            handlerDelay.postDelayed(runnable, delay * 2)
            isDeleteFinished = index == textSequenceToDelete.length
            _isFinished.postValue(false)
        } else {
            val txt = textSequenceToDelete.substring(0, index--)
            textSequenceToDelete = txt
            text = txt
            handlerDelay.postDelayed(runnable, delay * 2)
        }

        if (index < 0) {
            index = 0
        }
    }

    private fun addCharacter(runnable: Runnable) {
        text = textSequence.subSequence(0, index++)

        if (index <= textSequence.length) {
            handlerDelay.postDelayed(runnable, delay * 2)
            _isFinished.postValue(false)
        }

        if (index == textSequence.length) {
            _isFinished.postValue(true)
            isCreated = true
        }
    }

}