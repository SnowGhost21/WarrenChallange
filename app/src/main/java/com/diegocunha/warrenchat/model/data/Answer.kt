package com.diegocunha.warrenchat.model.data

import com.google.gson.annotations.SerializedName


data class Answer(val id: String,
                  val messages: List<Message>)

data class Message(val type: String,
                   @SerializedName("value") var message: String,
                   var sent: String) {
    companion object {
        const val USER = "user"
        const val BOT = "bot"
    }
}