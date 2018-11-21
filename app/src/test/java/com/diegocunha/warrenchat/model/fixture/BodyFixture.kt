package com.diegocunha.warrenchat.model.fixture

import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.data.Message

val createHistory = HashMap<String, Any>().apply {
    this["hello"] = "Hello"
    this["name"] = "Fulano"
}

val createBody = BodyMessage("abc", createHistory)

val createMessageUser = Message("string", "Hello", "user")

val createMessageBot = Message("string", "Hello my friend", "bot")

val createUserAnswer = Answer("abc", listOf(createMessageUser))

val createBotAnswer = Answer("def", listOf(createMessageBot))

val createInitialBodyMessage = BodyMessage(null, null)

val createUserBodyMessage = BodyMessage("name", createHistory)
