package com.diegocunha.warrenchat.model.fixture

import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.data.Message

val createHistory = HashMap<String, Any>()

val createBody = BodyMessage("abc", createHistory)

val createMessageUser = Message("string", "Hello", "user")

val createMessageBot = Message("string", "Hello my friend", "bot")

val createUserAnswer = Answer("abc", listOf(createMessageUser))

val createBotAnswer = Answer("def", listOf(createMessageBot))

