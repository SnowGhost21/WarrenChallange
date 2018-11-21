package com.diegocunha.warrenchat.view.home

import com.diegocunha.warrenchat.model.data.Message

open class MessageViewModel(val message: Message) {

    val receivedMessage = message.message

    val buttonAvailable = (message.buttons != null && !message.buttons!!.isEmpty())

    val buttonLeft = if (buttonAvailable) message.buttons!![0] else null

    val buttonRight = if (buttonAvailable && message.buttons!!.size > 1) message.buttons!![1] else null
}