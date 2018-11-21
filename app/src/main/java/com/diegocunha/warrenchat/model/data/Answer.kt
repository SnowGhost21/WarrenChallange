package com.diegocunha.warrenchat.model.data

import com.google.gson.annotations.SerializedName


data class Answer(
    val id: String,
    val messages: List<Message>,
    var buttons: List<Button> = emptyList(),
    var inputs: List<Input> = emptyList()
)

data class Message(
    val type: String,
    @SerializedName("value") var message: String,
    var sent: String,
    var buttons: List<Button>? = emptyList()
) {
    companion object {
        const val USER = "user"
        const val BOT = "bot"
    }
}

data class Button(val value: String, val label: Label)

data class Label(val title: String)

data class Input(val type: String, val mask: String)