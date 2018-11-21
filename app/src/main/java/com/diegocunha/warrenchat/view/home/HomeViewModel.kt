package com.diegocunha.warrenchat.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.data.Message
import com.diegocunha.warrenchat.model.repository.MessageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel constructor(private val repository: MessageRepository) : ViewModel() {

    private val _answers = MutableLiveData<Message>()
    val answers: LiveData<Message> = _answers
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val answersHistoric = HashMap<String, Any>()
    private var lastReceivedMessageId: String = ""
    private var initialMessageDisposable: Disposable? = null
    private var sendMessageDisposable: Disposable? = null

    init {
        initChat()
    }

    override fun onCleared() {
        super.onCleared()
        initialMessageDisposable?.dispose()
        sendMessageDisposable?.dispose()
    }

    private fun initChat() {
        initialMessageDisposable = repository.getInitialMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })
    }

    private fun configureMessage(answer: Answer, sent: String) {
        setLastReceivedId(answer.id)
        answer.messages.forEach {
            addMessage(it, sent)
        }
    }

    fun sendMessage(message: String) {
        val userMessage = Message("string", message, Message.USER)
        addMessage(userMessage)
        updateHistoricy(message)
        val bodyMessage = BodyMessage(lastReceivedMessageId, answersHistoric)

        sendMessageDisposable = repository.sendMessage(bodyMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })
    }

    private fun setLastReceivedId(id: String) {
        lastReceivedMessageId = id
    }

    private fun updateHistoricy(message: String) {
        answersHistoric[lastReceivedMessageId] = message
    }

    private fun addMessage(message: Message, sent: String) {
        message.sent = sent
        val cleanedMessage = cleanMessage(message)
        _answers.postValue(cleanedMessage)
    }

    private fun addMessage(message: Message) {
        val cleanedMessage = cleanMessage(message)
        _answers.postValue(cleanedMessage)
    }

    private fun cleanMessage(message: Message): Message {
        val messageArray = message.message.split(" ")
        messageArray.forEach { value ->
            if (value.contains("^")) {
                message.message = message.message.replace(value, "").replace("\\s+".toRegex(), " ")
            }
        }

        return message

    }
}

