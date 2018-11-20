package com.diegocunha.warrenchat.view.home

import com.diegocunha.warrenchat.model.data.Message

open class MessageViewModel(message: Message) {

    val receivedMessage = message.message
}