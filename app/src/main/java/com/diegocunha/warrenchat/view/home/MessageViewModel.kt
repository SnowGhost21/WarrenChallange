package com.diegocunha.warrenchat.view.home

import androidx.lifecycle.LiveData
import com.diegocunha.warrenchat.extensions.mutableLiveDataOf
import com.diegocunha.warrenchat.model.data.Message

open class MessageViewModel(val message: Message) {

    private val _userLetter = mutableLiveDataOf(message.userName?.first().toString())
    val userName: LiveData<String> = _userLetter

    val receivedMessage = message.message

    val buttonAvailable = (message.buttons != null && !message.buttons!!.isEmpty())

    val buttonLeft = if (buttonAvailable) message.buttons!![0] else null

    val buttonRight = if (buttonAvailable && message.buttons!!.size > 1) message.buttons!![1] else null
}