package com.diegocunha.warrenchat.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.data.Message
import com.diegocunha.warrenchat.model.repository.MessageRepository
import iwsbrazil.io.artmuseum.extensions.asLiveData

class HomeViewModel constructor(private val repository: MessageRepository) : ViewModel() {

    private val _answers = MutableLiveData<Message>()
    val answers: LiveData<Message> = _answers
    private val answersHistoric = HashMap<String, Any>()
    private var lastReceivedMessageId: String = ""

    init {
        initChat()
    }

    private fun initChat() {
        repository.getInitialMessage()
            .doOnSuccess { response ->
                setLastReceivedId(response.id)
                response.messages.forEach {
                    addMessage(it, Message.BOT)
                }
            }.asLiveData()
    }

    fun sendMessage(message: String) {
        val userMessage = Message("string", message, Message.USER)
        addMessage(userMessage)

        answersHistoric[lastReceivedMessageId] = message
        val bodyMessage = BodyMessage(lastReceivedMessageId, answersHistoric)
        repository.sendMessage(bodyMessage)
            .doOnSuccess {
                setLastReceivedId(it.id)
                it.messages.forEach { message ->
                    addMessage(message, Message.BOT)
                }
            }.asLiveData()
    }


    private fun addMessage(message: Message, sent: String) {
        message.sent = sent
        _answers.postValue(message)
    }

    private fun addMessage(message: Message) {
        _answers.postValue(message)
    }


    private fun setLastReceivedId(id: String) {
        lastReceivedMessageId = id
    }
}

